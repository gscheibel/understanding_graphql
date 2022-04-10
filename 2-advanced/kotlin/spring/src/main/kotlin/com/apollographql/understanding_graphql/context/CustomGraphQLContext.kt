package com.apollographql.understanding_graphql.context

import com.expediagroup.graphql.server.spring.execution.DefaultSpringGraphQLContextFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

@Component
class CustomGraphQLContextFactory : DefaultSpringGraphQLContextFactory() {
    override suspend fun generateContextMap(request: ServerRequest): Map<*, Any> {
        return super.generateContextMap(request) + mapOf(
            "role" to (request.headers().firstHeader("role") ?: "user")
        )
    }
}