import {ApolloServer} from "apollo-server";
import {ApolloServerPluginLandingPageGraphQLPlayground} from "apollo-server-core";
import {stitchSchemas} from "@graphql-tools/stitch";
import {remoteSchema} from "./remote_schema.js";

const missionSubGraph = await remoteSchema.make("http://localhost:8080/graphql")
const catSubGraph = await remoteSchema.make("http://localhost:4001/graphql")

const composedSchema = stitchSchemas({
    subschemas:[missionSubGraph, catSubGraph]
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