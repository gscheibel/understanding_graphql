package com.apollographql.understanding_graphql

import graphql.execution.instrumentation.tracing.TracingInstrumentation
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {
	@Bean
	fun hooks() = CustomHooksProvider()

	@Bean
	fun tracing() = TracingInstrumentation()
}

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
