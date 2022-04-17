package au.com.ricall.graphqldemo.model

import java.time.ZonedDateTime

data class Post(
    val id: String?,
    val slug: String,
    val thumbnail: String,
    val image: String,
    val content: String,
    val category: String,
    val tags: List<Tag>,
    val author: Author,

    val version: Long? = 0,
    val createdBy: String? = null,
    val createdDateTime: ZonedDateTime? = null,
    val modifiedBy: String? = null,
    val modifiedDateTime: ZonedDateTime? = null,
)

data class Tag(
    val slug: String,
    val name: String,
)

data class Author(
    val name: String,
    val thumbnail: String,
)
