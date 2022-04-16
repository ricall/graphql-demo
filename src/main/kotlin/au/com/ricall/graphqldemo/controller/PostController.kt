package au.com.ricall.graphqldemo.controller

import au.com.ricall.graphqldemo.model.Author
import au.com.ricall.graphqldemo.model.Post
import au.com.ricall.graphqldemo.model.Tag
import au.com.ricall.graphqldemo.service.PostService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono

@Controller
class PostController(val service: PostService) {

    @QueryMapping
    fun recentPosts(@Argument count: Long?, @Argument offset: Long?)
        = service.getRecentPosts(count, offset)

    @QueryMapping
    fun postsByCategory(
        @Argument category: String,
        @Argument count: Long?,
        @Argument offset: Long?
    ) = service.getPostsByCategory(category, count, offset)

    @QueryMapping
    fun postsByCategoryAndTag(
        @Argument category: String,
        @Argument tag: String,
        @Argument count: Long?,
        @Argument offset: Long?
    ) = service.getPostsByCategoryAndTag(category, tag, count, offset)

    @MutationMapping
    fun addPost(
        @Argument slug: String,
        @Argument thumbnail: String,
        @Argument image: String,
        @Argument content: String,
        @Argument category: String,
        @Argument tags: List<Tag>,
        @Argument author: Author,
    ): Mono<Post> {
        val post = Post(
            id = null,
            slug = slug,
            thumbnail = thumbnail,
            image = image,
            content = content,
            category = category,
            tags = tags,
            author = author
        )
        return service.save(post)
    }

}