package au.com.ricall.graphqldemo.controller

import au.com.ricall.graphqldemo.model.Author
import au.com.ricall.graphqldemo.model.Post
import au.com.ricall.graphqldemo.model.Tag
import au.com.ricall.graphqldemo.service.PostService
import au.com.ricall.graphqldemo.test.SpringBootTestWithoutMongoDB
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.graphql.test.tester.GraphQlTester
import reactor.core.publisher.Flux

@SpringBootTestWithoutMongoDB
class PostControllerTest {
    @Autowired
    lateinit var tester: GraphQlTester

    @MockBean
    lateinit var service: PostService

    @Test
    fun `verify we can get a post`() {
        whenever(service.getRecentPosts(1, 0)).thenReturn(Flux.fromIterable(listOf(Post(
            id = "id",
            slug = "post-slug",
            image = "image",
            thumbnail = "thumbnail",
            content = "content",
            category = "category",
            tags = listOf(Tag(
                slug = "tag-slug",
                name = "name",
            )),
            author = Author(
                name = "author-name",
                thumbnail = "thumbnail"
            ),
        ))))

        tester.document("""
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
            }
        """.trimIndent())
            .execute()
            .path("recentPosts[0].id").entity(String::class.java).isEqualTo("id")
            .path("recentPosts[0].slug").entity(String::class.java).isEqualTo("post-slug")
            .path("recentPosts[0].content").entity(String::class.java).isEqualTo("content")
            .path("recentPosts[0].category").entity(String::class.java).isEqualTo("category")
            .path("recentPosts[0].tags[0].slug").entity(String::class.java).isEqualTo("tag-slug")
            .path("recentPosts[0].tags[0].name").entity(String::class.java).isEqualTo("name")
            .path("recentPosts[0].author.name").entity(String::class.java).isEqualTo("author-name")
            .path("recentPosts[0].author.thumbnail").entity(String::class.java).isEqualTo("thumbnail")
    }

}
