package au.com.ricall.graphqldemo.repository

import au.com.ricall.graphqldemo.entity.PostEntity
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
@ConditionalOnProperty(value = ["app.mongodb"], havingValue = "true")
interface PostRepository : ReactiveCrudRepository<PostEntity, String> {

    fun findAllByCategory(category: String): Flux<PostEntity>
}
