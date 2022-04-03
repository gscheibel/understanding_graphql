package com.apollographql.understanding_graphql

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {
	@Bean
	fun hooks() = CustomHooksProvider()
}

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
