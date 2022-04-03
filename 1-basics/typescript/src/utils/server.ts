import { ApolloServer } from "apollo-server";
import {
    ApolloServerPluginLandingPageGraphQLPlayground
} from "apollo-server-core";
import { ApolloServerExpressConfig } from "apollo-server-express";

export const runner = (config: ApolloServerExpressConfig) => {
    config.plugins = [ApolloServerPluginLandingPageGraphQLPlayground()]
    const server = new ApolloServer(config);

    server.listen().then(({ url }) => {
        console.log(`
          🚀  Server is running!
          🔉  Listening on port 4000
          🚀 Server ready at ${url}
        `);
    })
}