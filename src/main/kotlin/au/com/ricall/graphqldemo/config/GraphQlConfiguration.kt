package au.com.ricall.graphqldemo.config

import graphql.scalars.ExtendedScalars
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer

@Configuration
class GraphQlConfiguration {

    @Bean
    fun runtimeWiringConfigurer() = RuntimeWiringConfigurer {
        builder -> builder
        .scalar(ExtendedScalars.Date)
        .scalar(ExtendedScalars.DateTime)
    }

}