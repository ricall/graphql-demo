package au.com.ricall.graphqldemo.entity

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("Post")
data class PostEntity(
    @Id
    val id: String? = null,
    val slug: String,
    val thumbnail: String,
    val image: String,
    val content: String,
    val category: String,
    val tags: Set<TagEntity>,
    val author: AuthorEntity,

    @Version val version: Long = 0,
    @CreatedBy val createdBy: String? = null,
    @CreatedDate val createdDateTime: LocalDateTime? = null,
    @LastModifiedBy val modifiedBy: String? = null,
    @LastModifiedDate val modifiedDateTime: LocalDateTime? = null,
)

data class TagEntity(
    @Id
    val id: String,
    val slug: String,
    val name: String,
)

data class AuthorEntity(
    @Id
    val id: String,
    val name: String,
    val thumbnail: String,
)