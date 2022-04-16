package au.com.ricall.graphqldemo.test

import au.com.ricall.graphqldemo.SimpleApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.lang.annotation.Inherited


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Inherited
@SpringBootTest(classes = [SimpleApplication::class])
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = [
    MongoAutoConfiguration::class,
    MongoReactiveAutoConfiguration::class,
    MongoDataAutoConfiguration::class,
])
@AutoConfigureGraphQlTester
annotation class SpringBootTestWithoutMongoDB()
