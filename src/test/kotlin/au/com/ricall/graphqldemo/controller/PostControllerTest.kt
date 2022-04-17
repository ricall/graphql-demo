package au.com.ricall.graphqldemo.controller

import au.com.ricall.graphqldemo.entity.AuthorEntity
import au.com.ricall.graphqldemo.entity.PostEntity
import au.com.ricall.graphqldemo.entity.TagEntity
import au.com.ricall.graphqldemo.repository.PostRepository
import au.com.ricall.graphqldemo.test.SpringBootTestWithoutMongoDB
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.graphql.test.tester.GraphQlTester.Traversable
import reactor.core.publisher.Flux

@SpringBootTestWithoutMongoDB
class PostControllerTest {
    @Autowired
    lateinit var tester: GraphQlTester

    @MockBean
    lateinit var repository: PostRepository

    fun Traversable.verifyPath(path: String) = path(path).entity(String::class.java)

    fun execute(graphql: String) = tester.document(graphql).execute()

    @AfterEach
    fun cleanup() = verifyNoMoreInteractions(repository)

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

        val response = execute(
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

        verify(repository).findAll()
        response
            .verifyPath("recentPosts[0].id").isEqualTo("id")
            .verifyPath("recentPosts[0].slug").isEqualTo("post-slug")
            .verifyPath("recentPosts[0].content").isEqualTo("content")
            .verifyPath("recentPosts[0].category").isEqualTo("category")
            .verifyPath("recentPosts[0].tags[0].slug").isEqualTo("tag-slug")
            .verifyPath("recentPosts[0].tags[0].name").isEqualTo("name")
            .verifyPath("recentPosts[0].author.name").isEqualTo("author-name")
            .verifyPath("recentPosts[0].author.thumbnail").isEqualTo("thumbnail")
    }
}
