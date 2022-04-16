package au.com.ricall.graphqldemo

import au.com.ricall.graphqldemo.entity.AuthorEntity
import au.com.ricall.graphqldemo.entity.PostEntity
import au.com.ricall.graphqldemo.entity.TagEntity
import au.com.ricall.graphqldemo.repository.PostRepository
import com.github.javafaker.Faker
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import java.util.*
import kotlin.random.Random

@Component
@ConditionalOnProperty(value = ["app.mongodb"], havingValue = "true")
class DataLoader(val repository: PostRepository): CommandLineRunner {

    val log: Logger = LoggerFactory.getLogger(Application::class.java)
    val faker = Faker()

    private fun toSlug(prefix: String, text: String)
        = "${prefix}-${text.replace(' ', '-').lowercase().replace("[^a-z-]+".toRegex(), "")}"

    private suspend fun createDummyPosts() {
        log.info("Loading Data")
        repeat(200) {
            val post = repository.save(
                PostEntity(
                    slug = toSlug("post", faker.book().title()),
                    thumbnail = "https://via.placeholder.com/64x64",
                    image = "https://via.placeholder.com/600x480",
                    content = faker.lorem().paragraphs(4).joinToString(),
                    category = faker.animal().name(),
                    tags = (1..Random.nextInt(10)).map {
                        faker.artist().name().let { name ->
                            TagEntity(
                                id = UUID.randomUUID().toString(),
                                slug = toSlug("tag", name),
                                name = name
                            )
                        }
                    }.toSet(),
                    author = AuthorEntity(
                        id = UUID.randomUUID().toString(),
                        name = faker.name().fullName(),
                        thumbnail = faker.avatar().image()
                    )
                )
            ).awaitSingle()
            log.info("post: {}", post)
        }
    }

    override fun run(vararg args: String?) {
        runBlocking {
            if (repository.findAll().awaitFirstOrNull() == null) {
                createDummyPosts()
            }
        }

    }
}