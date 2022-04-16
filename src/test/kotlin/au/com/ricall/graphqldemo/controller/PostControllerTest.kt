package au.com.ricall.graphqldemo.controller

import au.com.ricall.graphqldemo.entity.AuthorEntity
import au.com.ricall.graphqldemo.entity.PostEntity
import au.com.ricall.graphqldemo.entity.TagEntity
import au.com.ricall.graphqldemo.repository.PostRepository
import au.com.ricall.graphqldemo.test.SpringBootTestWithoutMongoDB
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.graphql.test.tester.GraphQlTester.Path
import reactor.core.publisher.Flux

@SpringBootTestWithoutMongoDB
class PostControllerTest {
    @Autowired
    lateinit var tester: GraphQlTester

    @MockBean
    lateinit var repository: PostRepository

    fun Path.asString() = this.entity(String::class.java)

    fun execute(graphql: String) = tester.document(graphql.trimMargin()).execute()

    @Test
    fun `verify we can get a post`() {
        whenever(repository.findAll()).thenReturn(
            Flux.fromIterable(
                listOf(
                    PostEntity(
                        id = "id",
                        slug = "post-slug",
                        image = "image",
                        thumbnail = "thumbnail",
                        content = "content",
                        category = "category",
                        tags = setOf(
                            TagEntity(
                                id = "id",
                                slug = "tag-slug",
                                name = "name",
                            )
                        ),
                        author = AuthorEntity(
                            id = "id",
                            name = "author-name",
                            thumbnail = "thumbnail"
                        ),
                    )
                )
            )
        )

        execute(
            """
            {
              recentPosts(count: 1, offset: 0) {
                id
                slug
                content
                category
                tags {
                  slug
                  name
                }
                author {
                  name
                  thumbnail
                }
              }
            }"""
        )
            .path("recentPosts[0].id").asString().isEqualTo("id")
            .path("recentPosts[0].slug").asString().isEqualTo("post-slug")
            .path("recentPosts[0].content").asString().isEqualTo("content")
            .path("recentPosts[0].category").asString().isEqualTo("category")
            .path("recentPosts[0].tags[0].slug").asString().isEqualTo("tag-slug")
            .path("recentPosts[0].tags[0].name").asString().isEqualTo("name")
            .path("recentPosts[0].author.name").asString().isEqualTo("author-name")
            .path("recentPosts[0].author.thumbnail").asString().isEqualTo("thumbnail")
    }

}
