import {gql} from "apollo-server";
import {runner} from "./utils/server";


const typeDefs = gql`
    "We can have some documentation right on the type definition"
    type Query {
        
        "But also on fields themselves"
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
    Query: {}
}

runner({typeDefs, resolvers})