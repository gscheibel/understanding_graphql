import {ApolloServer, gql} from "apollo-server";
import {ApolloServerPluginLandingPageGraphQLPlayground} from "apollo-server-core";
import {stitchSchemas} from "@graphql-tools/stitch";
import { delegateToSchema } from '@graphql-tools/delegate'
import {remoteSchema} from "./remote_schema.js";
import {GraphQLSchema} from "graphql";
import {OperationTypeNode} from "graphql/language/ast";

const missionSubGraph: GraphQLSchema = await remoteSchema.make("http://localhost:8080/graphql")
const catSubGraph: GraphQLSchema = await remoteSchema.make("http://localhost:4001/graphql")

const composedSchema: GraphQLSchema = stitchSchemas({
    subschemas: [missionSubGraph, catSubGraph],
    typeDefs: gql `
        extend type SpaceCat {
            mission: Mission
        }
    `,
    resolvers: {
        SpaceCat: {
            mission: {
                selectionSet: `{ id }`,
                resolve: (cat, args, context, info) => {
                    return delegateToSchema({
                        schema: missionSubGraph,
                        operation: OperationTypeNode.QUERY,
                        fieldName: 'catById',
                        args: {id: cat.id },
                        context,
                        info
                    })
                }
            }
        }
    }
})
const server = new ApolloServer({
    schema: composedSchema,
    plugins: [ApolloServerPluginLandingPageGraphQLPlayground()]
});

server.listen().then(({url}) => {
    console.log(`
    🚀  Server is running!
    🔉  Listening on port 4000
    🚀 Server ready at ${url}
  `);
})