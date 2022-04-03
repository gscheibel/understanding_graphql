package com.apollographql.understanding_graphql.directives

import com.expediagroup.graphql.generator.annotations.GraphQLDirective
import com.expediagroup.graphql.generator.directives.KotlinFieldDirectiveEnvironment
import com.expediagroup.graphql.generator.directives.KotlinSchemaDirectiveWiring
import com.expediagroup.graphql.generator.exceptions.GraphQLKotlinException
import graphql.introspection.Introspection.DirectiveLocation
import graphql.schema.DataFetcher
import graphql.schema.GraphQLFieldDefinition

const val ADMIN_DIRECTIVE_NAME = "adminOnly"

@GraphQLDirective(
    name = ADMIN_DIRECTIVE_NAME,
    description = "Will break if the user is not an admin",
    locations = [DirectiveLocation.FIELD_DEFINITION]
)
annotation class AdminOnlyDirective

class NotAnAdminException: GraphQLKotlinException(message = "The user must be an admin to access this field")
class AdminOnlyDirectiveWiring : KotlinSchemaDirectiveWiring {
    override fun onField(environment: KotlinFieldDirectiveEnvironment): GraphQLFieldDefinition {
        val field = environment.element
        val originalDataFetcher = environment.getDataFetcher()

        val adminOnlyDataFetcher = DataFetcher { dfe ->
            val role = dfe.graphQlContext.get<String>("role")
            if(!role.equals("admin")) throw NotAnAdminException()
            originalDataFetcher.get(dfe)
        }

        environment.setDataFetcher(adminOnlyDataFetcher)

        return field
    }
}