package com.apollographql.understanding_graphql

import com.apollographql.understanding_graphql.directives.AdminOnlyDirective
import com.apollographql.understanding_graphql.directives.LowerCaseDirective
import com.apollographql.understanding_graphql.instrumentation.TrackInvocationDirective
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.util.*

@Component
class SpaceCatQuery(val mission: Mission) : Query {

    @TrackInvocationDirective
    fun cat(id: Int = 0): SpaceCat {
        return SpaceCat(name = "Neil Catstrong")
    }

    @AdminOnlyDirective
    fun where() = mission
    fun withUUID(): UUID = UUID.randomUUID()
    fun withUUIDAsString(): String = UUID.randomUUID().toString()

    fun withUUIDFromString(uuid: String): String = UUID.fromString(uuid).toString()
}

data class SpaceCat(
    val name: String
) {
    fun `fun`(dfe: DataFetchingEnvironment): String {
        return "String"
    }
}

@Component
class Mission {
    fun where() = "over the rainbow"
}