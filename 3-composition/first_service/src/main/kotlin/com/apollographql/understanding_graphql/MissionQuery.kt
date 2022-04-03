package com.apollographql.understanding_graphql

import com.expediagroup.graphql.server.operations.Query
import org.springframework.stereotype.Component

@Component
class MissionQuery : Query {
    fun mission() = Mission()
}

enum class Orbit {
    LOW, HIGH
}

class Mission {
    val where = "over the rainbow"
    val orbit = Orbit.HIGH
}