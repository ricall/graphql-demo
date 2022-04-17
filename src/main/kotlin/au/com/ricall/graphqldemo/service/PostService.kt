package au.com.ricall.graphqldemo.service

import au.com.ricall.graphqldemo.entity.AuthorEntity
import au.com.ricall.graphqldemo.entity.PostEntity
import au.com.ricall.graphqldemo.entity.TagEntity
import au.com.ricall.graphqldemo.model.Author
import au.com.ricall.graphqldemo.model.Post
import au.com.ricall.graphqldemo.model.Tag
import au.com.ricall.graphqldemo.repository.PostRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.ZoneId
import java.util.UUID

@Service
class PostService(private val repository: PostRepository) {
    companion object {
        private const val DEFAULT_COUNT = 10L
        private const val DEFAULT_OFFSET = 0L
    }

    fun getRecentPosts(count: Long?, offset: Long?): Flux<Post> = withPaging(count, offset, repository.findAll())

    fun getPostsByCategory(category: String, count: Long?, offset: Long?) =
        withPaging(count, offset, repository.findAllByCategory(category))

    fun getPostsByCategoryAndTag(category: String, tag: String, count: Long?, offset: Long?) =
        withPaging(
            count,
            offset,
            repository.findAllByCategory(category)
                .filter { post -> post.tags.find { it.name == tag } != null }
        )

    private fun withPaging(count: Long?, offset: Long?, results: Flux<PostEntity>) = results
        .skip(offset ?: DEFAULT_OFFSET)
        .take(count ?: DEFAULT_COUNT)
        .map(::toPost)

    fun save(post: Post): Mono<Post> = repository.save(toPostEntity(post))
        .map(::toPost)

    private fun toPost(post: PostEntity): Post = with(post) {
        Post(
            id = id,
            slug = slug,
            thumbnail = thumbnail,
            image = image,
            content = content,
            category = category,
            tags = tags.map { tag ->
                Tag(
                    slug = tag.slug,
                    name = tag.name
                )
            },
            author = with(author) {
                Author(
                    name = name,
                    thumbnail = thumbnail,
                )
            },
            version = version,
            createdBy = createdBy,
            createdDateTime = createdDateTime?.atZone(ZoneId.of("UTC")),
            modifiedBy = modifiedBy,
            modifiedDateTime = modifiedDateTime?.atZone(ZoneId.of("UTC"))
        )
    }

    private fun toPostEntity(post: Post): PostEntity = with(post) {
        PostEntity(
            id = id,
            slug = slug,
            thumbnail = thumbnail,
            image = image,
            content = content,
            category = category,
            tags = tags.map { tag ->
                TagEntity(
                    id = UUID.randomUUID().toString(),
                    slug = tag.slug,
                    name = tag.name
                )
            }.toSet(),
            author = with(author) {
                AuthorEntity(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    thumbnail = thumbnail
                )
            },
            version = version ?: DEFAULT_OFFSET,
            createdBy = createdBy,
            createdDateTime = createdDateTime?.toLocalDateTime(),
            modifiedBy = modifiedBy,
            modifiedDateTime = modifiedDateTime?.toLocalDateTime()
        )
    }
}
