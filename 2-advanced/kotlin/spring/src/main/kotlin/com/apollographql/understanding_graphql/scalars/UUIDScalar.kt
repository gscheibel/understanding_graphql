package com.apollographql.understanding_graphql.scalars

import graphql.language.StringValue
import graphql.schema.*
import java.util.*

val graphqlUUID = GraphQLScalarType.newScalar()
    .name("UUID")
    .description("Graph version for java.util.UUID")
    .coercing(UUIDCoercing)
    .build()

object UUIDCoercing : Coercing<UUID, String> {
    override fun serialize(dataFetcherResult: Any): String {
        return kotlin.runCatching { dataFetcherResult.toString() }.getOrElse { throw CoercingSerializeException("Cannot serialize $dataFetcherResult as string") }
    }

    override fun parseValue(input: Any): UUID = runCatching {
        UUID.fromString(serialize(input))
    }.getOrElse {
        throw CoercingParseValueException("Expected valid UUID but was $input")
    }

    override fun parseLiteral(input: Any): UUID {
        val uuidString = (input as? StringValue)?.value
        return runCatching {
            UUID.fromString(uuidString)
        }.getOrElse {
            throw CoercingParseLiteralException("Expected valid UUID literal but was $uuidString")
        }
    }
}