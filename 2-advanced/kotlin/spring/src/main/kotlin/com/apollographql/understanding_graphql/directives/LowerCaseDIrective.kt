package com.apollographql.understanding_graphql.directives

import com.expediagroup.graphql.generator.annotations.GraphQLDirective
import com.expediagroup.graphql.generator.directives.KotlinFieldDirectiveEnvironment
import com.expediagroup.graphql.generator.directives.KotlinSchemaDirectiveWiring
import graphql.introspection.Introspection
import graphql.schema.DataFetcherFactories
import graphql.schema.GraphQLFieldDefinition


const val LOWERCASE_DIRECTIVE_NAME = "lowerCase"

@GraphQLDirective(
    name = LOWERCASE_DIRECTIVE_NAME,
    description = "no upper case characters returned",
    locations = [Introspection.DirectiveLocation.FIELD_DEFINITION]
)
annotation class LowerCaseDirective


class LowerCaseDirectiveWiring : KotlinSchemaDirectiveWiring {
    override fun onField(environment: KotlinFieldDirectiveEnvironment): GraphQLFieldDefinition {
        val field = environment.element
        val originalDataFetcher = environment.getDataFetcher()

        val lowerCasingDataFetcher = DataFetcherFactories.wrapDataFetcher(
            originalDataFetcher
        ) { _, value -> value.toString().lowercase() }


        environment.setDataFetcher(lowerCasingDataFetcher)

        return field
    }
}