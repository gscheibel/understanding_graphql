package com.apollographql.understanding_graphql.instrumentation

import com.expediagroup.graphql.generator.annotations.GraphQLDirective
import graphql.ExecutionResult
import graphql.execution.instrumentation.InstrumentationContext
import graphql.execution.instrumentation.InstrumentationState
import graphql.execution.instrumentation.SimpleInstrumentation
import graphql.execution.instrumentation.SimpleInstrumentationContext
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters
import graphql.introspection.Introspection
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap


const val TRACK_TIMES_INVOKED_DIRECTIVE_NAME = "track"

@GraphQLDirective(
    name = TRACK_TIMES_INVOKED_DIRECTIVE_NAME,
    locations = [Introspection.DirectiveLocation.FIELD_DEFINITION]
)
annotation class TrackInvocationDirective


@Component
class TrackTimesInvokedInstrumentation : SimpleInstrumentation() {

    private val logger = LoggerFactory.getLogger(TrackTimesInvokedInstrumentation::class.java)

    override fun createState(): InstrumentationState = TrackTimesInvokedInstrumenationState()

    override fun beginFieldFetch(parameters: InstrumentationFieldFetchParameters): InstrumentationContext<Any> {
        if (parameters.field.getDirective(TRACK_TIMES_INVOKED_DIRECTIVE_NAME) != null) {
            (parameters.getInstrumentationState() as? TrackTimesInvokedInstrumenationState)?.incrementCount(parameters.field.name)
        }

        return SimpleInstrumentationContext()
    }

    override fun instrumentExecutionResult(executionResult: ExecutionResult, parameters: InstrumentationExecutionParameters): CompletableFuture<ExecutionResult> {
        val count = (parameters.getInstrumentationState() as? TrackTimesInvokedInstrumenationState)?.getCount()
        logger.info("Fields invoked: $count")
        return super.instrumentExecutionResult(executionResult, parameters)
    }

    /**
     * The state per execution for this Instrumentation
     */
    private class TrackTimesInvokedInstrumenationState : InstrumentationState {

        private val fieldCount = ConcurrentHashMap<String, Int>()

        fun incrementCount(fieldName: String) {
            val currentCount = fieldCount.getOrDefault(fieldName, 0)
            fieldCount[fieldName] = currentCount.plus(1)
        }

        fun getCount() = fieldCount.toString()
    }
}