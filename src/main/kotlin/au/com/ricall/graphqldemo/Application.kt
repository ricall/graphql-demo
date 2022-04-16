package au.com.ricall.graphqldemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoAuditing
@EnableReactiveMongoRepositories
@ConfigurationPropertiesScan
@ConditionalOnProperty(value = ["app.mongodb"], havingValue = "true")
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}