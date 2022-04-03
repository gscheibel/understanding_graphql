import { ApolloServer, gql } from "apollo-server";
import {
  ApolloServerPluginLandingPageGraphQLPlayground
} from "apollo-server-core";
import { runner } from "./utils/server";


const typeDefs = gql`
   type Query {
      integer: Int 
      float: Float
      yesno: Boolean
      listOfStrings: [String]
      enum: Enumeration 
   }

   enum Enumeration {
       FIRST_VALUE, SECOND_VALUE
   }


`;


const resolvers = {
  Query: {
   
  }
}

runner({typeDefs, resolvers})