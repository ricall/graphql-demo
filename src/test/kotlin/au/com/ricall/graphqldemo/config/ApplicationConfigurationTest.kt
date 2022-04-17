package au.com.ricall.graphqldemo.config

import au.com.ricall.graphqldemo.repository.PostRepository
import au.com.ricall.graphqldemo.test.SpringBootTestWithoutMongoDB
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verifyNoMoreInteractions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTestWithoutMongoDB
class ApplicationConfigurationTest {

    @Autowired
    lateinit var configuration: ApplicationConfiguration

    @MockBean
    lateinit var repository: PostRepository

    @AfterEach
    fun cleanup() = verifyNoMoreInteractions(repository)

    @Test
    fun `verify configuration is populated correctly`() {
        assertThat(configuration.mongodb).isEqualTo(false)
    }
}
