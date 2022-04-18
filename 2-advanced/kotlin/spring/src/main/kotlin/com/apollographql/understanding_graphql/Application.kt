package com.apollographql.understanding_graphql

import com.apollographql.understanding_graphql.configuration.CustomSchemaGeneratorHooks
import graphql.execution.instrumentation.tracing.TracingInstrumentation
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {
	@Bean
	fun hooks() = CustomSchemaGeneratorHooks()

	@Bean
	fun tracing() = TracingInstrumentation()
}

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
