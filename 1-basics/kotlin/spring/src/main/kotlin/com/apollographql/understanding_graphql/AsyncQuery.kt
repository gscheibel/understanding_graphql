package com.apollographql.understanding_graphql

import com.apollographql.understanding_graphql.MissionLength.*
import com.expediagroup.graphql.server.operations.Query
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class AsyncQuery : Query {
    fun asyncIsBetter() = AsyncSpaceCat("ThreadyCat")
    fun realAsyncIsBetter() = AsyncCoroutinedSpaceCat()
}

class AsyncSpaceCat(val name: String) {

    fun missions(): List<AsyncMission> {
        return listOf(simpleMissions(), longMissions()).flatten()
    }

     fun simpleMissions(): List<AsyncMission> {
        println("Start short threaded process")
        Thread.sleep(500)
        val list = listOf(AsyncMission(length = SHORT), AsyncMission(length = SHORT))
        println("Done short threaded process")
        return list
    }

     fun longMissions(): List<AsyncMission> {
        println("Start long threaded process")
        Thread.sleep(2000)
        val list = listOf(AsyncMission(length = LONG), AsyncMission(length = LONG))
        println("Done long threaded process")
        return list
    }
}

class AsyncCoroutinedSpaceCat(val name: String = "CoroutinedSpaceCats"): Query{

    suspend fun missions(): List<AsyncMission> = coroutineScope {
        val simples = async { simpleMissions() }
        val longs = async { longMissions() }

        listOf(simples.await(), longs.await()).flatten()
    }

    suspend fun simpleMissions(): List<AsyncMission> {
        println("Start short coroutined process")
        delay(500)
        val list = listOf(AsyncMission(length = SHORT), AsyncMission(length = SHORT))
        println("Done short coroutined process")
        return list
    }

    suspend fun longMissions(): List<AsyncMission> {
        println("Start long coroutined process")
        delay(2000)
        val list = listOf(AsyncMission(length = LONG), AsyncMission(length = LONG))
        println("Done long coroutined process")
        return list
    }
}

enum class MissionLength {
    LONG, SHORT
}

data class AsyncMission(val location: String = "Somewhere cool ${Random.nextInt(100)}", val length: MissionLength)