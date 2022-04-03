import { gql } from "apollo-server";
import { GraphQLScalarType, Kind, ValueNode } from "graphql";
import { runner } from "./utils/server";


const typeDefs = gql`
   type Query {
    website: Website
   }

   type Website {
    title: String!
    url: URL!
   }

   scalar URL
`;

class Website {
    title: string;
    url: URL;

    constructor(title: string, url: URL) {
        this.title = title;
        this.url = url;
    }
}
const urlResolver = new GraphQLScalarType({
    name: "URL",
    description: "A valid URL",
    serialize: (value): string => new URL(value.toString()).toString(),
    parseValue: (value: string): URL => new URL(value),
    parseLiteral: (valueAST: ValueNode): URL => {
        console.log(`parse literal = ${valueAST.kind} ${valueAST}`)
        if (valueAST.kind !== Kind.STRING) {
            throw new Error("The value must be a string")
        }
        return new URL(valueAST.value);
    }
});

const resolvers = {
    URL: urlResolver,
    Query: {
        website: (): Website => {
            return {
                title: "This website is awesome",
                url: new URL("http://fooo.bar")
            }
        }
    }
}

runner({ typeDefs, resolvers })