import { gql } from "apollo-server";
import { runner } from "./utils/server"
import { mapSchema, getDirective, MapperKind } from "@graphql-tools/utils";
import { GraphQLSchema, defaultFieldResolver } from "graphql";
import { makeExecutableSchema } from "@graphql-tools/schema";

/**
 * Example of a Schema directive. How to apply a behaviour when a query is resolved.
 */

const typeDefs = gql`
  directive @uppercase on FIELD_DEFINITION 

  type Query {
    greeting: String! @uppercase
    greeting2: String!
  }
`;

function uppercaseDirective(schema: GraphQLSchema): GraphQLSchema {
  return mapSchema(schema, {
      [MapperKind.OBJECT_FIELD]: fieldConfig => {
        const upperDirective = getDirective(schema, fieldConfig, "uppercase")?.[0];

        if (upperDirective) {
          const { resolve = defaultFieldResolver } = fieldConfig

          return {
            ...fieldConfig,
            resolve: async (source, args, context, info) => {
              const res = await resolve(source, args, context, info);

              if (typeof res === 'string') {
                return res.toUpperCase()
              }
            }
          }
        }

      }
    })
}


const resolvers = {
  Query: {
    greeting: () => "Hello World",
    greeting2: () => "Hello World"
  }
}

let schema = makeExecutableSchema({
  typeDefs, resolvers
})

runner(
  {schema: uppercaseDirective(schema)}
)