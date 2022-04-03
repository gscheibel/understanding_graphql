package com.apollographql.understanding_graphql

import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class SpaceCatQuery(val mission: Mission) : Query {
    fun cat(id: Int = 0): SpaceCat {
        return SpaceCat(name = "Neil Catstrong")
    }

    fun where() = mission
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