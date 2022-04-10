import { ApolloServer, gql } from "apollo-server";
import {
  ApolloServerPluginLandingPageGraphQLPlayground
} from "apollo-server-core";


const typeDefs = gql`
   type Query {
      cat: SpaceCat!
      catById(id: Int): SpaceCat!
   }

   type SpaceCat {
      id: Int
      name: String
   }
`;

const resolvers = {
  Query: {
    cat: () => {
      return {
        name: "Neew Catstrong",
        id: 1
      }
    },

    catById: (id: Number) => {
        return {
            name: `Neew Catstrong - ${id}`,
            id: id
        }
    }
  }
}

const server = new ApolloServer({
  typeDefs,
  resolvers,
  plugins: [ApolloServerPluginLandingPageGraphQLPlayground()]
});

server.listen({port: 4001}).then(({ url }) => {
  console.log(`
    🚀  Server is running!
    🔉  Listening on port 4000
    🚀 Server ready at ${url}
  `);
})