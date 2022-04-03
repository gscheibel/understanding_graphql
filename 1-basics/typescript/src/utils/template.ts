import { gql } from "apollo-server";
import { runner } from "./server";

const typeDefs = gql`
   type Query {
      hello: String!
      cat: SpaceCat!
   }

   type SpaceCat {
      id: ID
      name: String
   }
`;

const resolvers = {
    
}


runner( {
    typeDefs,
    resolvers
})
