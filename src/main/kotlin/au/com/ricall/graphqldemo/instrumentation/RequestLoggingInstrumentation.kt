package au.com.ricall.graphqldemo.instrumentation

import graphql.ExecutionResult
import graphql.execution.instrumentation.InstrumentationContext
import graphql.execution.instrumentation.SimpleInstrumentation
import graphql.execution.instrumentation.SimpleInstrumentationContext
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Clock
import java.time.Duration
import java.time.Instant

@Component
class RequestLoggingInstrumentation(val clock: Clock) : SimpleInstrumentation() {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun beginExecution(
        parameters: InstrumentationExecutionParameters?
    ): InstrumentationContext<ExecutionResult> {
        val start = Instant.now(clock)
        val executionId = parameters?.executionInput?.executionId
        log.info(
            "{}: query: {} with variables: {}",
            executionId,
            parameters?.query?.replace(Regex("\\s+"), " "),
            parameters?.variables
        )
        return SimpleInstrumentationContext.whenCompleted { _, throwable ->
            val duration = Duration.between(start, Instant.now(clock))
            if (throwable == null) {
                log.info("{}: completed successfully in {}ms", executionId, duration.toMillis())
            } else {
                log.warn("{}: execution failed in {}ms", executionId, duration.toMillis(), throwable)
            }
        }
    }
}
