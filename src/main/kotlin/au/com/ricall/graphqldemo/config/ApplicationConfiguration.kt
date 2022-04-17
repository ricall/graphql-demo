package au.com.ricall.graphqldemo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app")
class ApplicationConfiguration(
    var mongodb: Boolean = true
)
