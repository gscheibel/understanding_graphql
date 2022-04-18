package com.apollographql.understanding_graphql.configuration

import com.apollographql.understanding_graphql.directives.ADMIN_DIRECTIVE_NAME
import com.apollographql.understanding_graphql.directives.AdminOnlyDirectiveWiring
import com.apollographql.understanding_graphql.directives.LOWERCASE_DIRECTIVE_NAME
import com.apollographql.understanding_graphql.directives.LowerCaseDirectiveWiring
import com.apollographql.understanding_graphql.scalars.graphqlUUID
import com.expediagroup.graphql.generator.directives.KotlinDirectiveWiringFactory
import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import com.expediagroup.graphql.plugin.schema.hooks.SchemaGeneratorHooksProvider
import graphql.schema.GraphQLType
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KType

class CustomSchemaGeneratorHooks : SchemaGeneratorHooks {
    override fun willGenerateGraphQLType(type: KType): GraphQLType? {
        return when (type.classifier as? KClass<*>) {
            UUID::class -> graphqlUUID
            else -> null
        }
    }

    override val wiringFactory: KotlinDirectiveWiringFactory
        get() = customWiringFactory
}

val customWiringFactory = KotlinDirectiveWiringFactory(
    manualWiring = mapOf(
        LOWERCASE_DIRECTIVE_NAME to LowerCaseDirectiveWiring(),
        ADMIN_DIRECTIVE_NAME to AdminOnlyDirectiveWiring()
    )
)
