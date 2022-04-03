
import { gql } from "apollo-server";
import { runner } from "./utils/server";
import { catsAndMissions, SpaceCat } from "./utils/spaceCat";

const typeDefs = gql`
    type SpaceCat {
        id: Int!
        name: String!
        missions: [Mission!]
    }

    type Mission {
        name: String!
    }

    type Query {
        spaceCatsById(id: Int!): SpaceCat
        spaceCatsByCatId(catId: CatId!): SpaceCat
    }

    input CatId {
        id: Int!
    }
`
const resolvers = {
    Query: {
        spaceCatsById: (parent, args, context, info): SpaceCat => {
            console.table(catsAndMissions);
            return catsAndMissions.find((cat: SpaceCat) => cat.id == args.id);
        },

        spaceCatsByCatId: (parent, args, context, info): SpaceCat => {
            return resolvers.Query.spaceCatsById(parent, { id: args.catId.id }, context, info);
        }
    }
}

runner({ typeDefs, resolvers })