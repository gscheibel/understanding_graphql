/**
 * Goal: demonstrate the usage of a sub resolver and the fact it's only invoked when the field is selected
 * 
 * ```
 * {
 *   spaceCatsById(id: 0){
 *     id
 *     name
 *   }
 * }
 * ```
 * 
 *  * ```
 * {
 *   spaceCatsById(id: 0){
 *     id
 *     name
 *     missions{
 *       name
 *     }
 *   }
 * }
 * ```
 */

import { gql } from "apollo-server";
import { runner } from "./utils/server";
import { SpaceCat, Mission, catsAndMissions } from "./utils/spaceCat";

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
        neil: SpaceCat
        spaceCatsById(id: Int!): SpaceCat
    }
`

const missionAndCatsId = [
    { catId: 0, missions: [new Mission("Mission 1 for cat 0"), new Mission("Mission 2 for cat 0")] },
    { catId: 1, missions: [new Mission("Mission 1 for cat 1")] }
]

const resolvers = {
    Query: {
        neil: (parent, args, context, info): SpaceCat => {
            return new SpaceCat(0, "Neil Catstrong");
        },

        spaceCatsById: (parent, args, context, info): SpaceCat => {
            return catsAndMissions.find( (cat: SpaceCat) => { console.log(`${args.id} ${cat.id}`); return cat.id == args.id} );
        }
    },

    SpaceCat: {
        missions: (parent, args, context, info): Mission[] => {
            console.table(parent);
            return missionAndCatsId.find( (missions) => missions.catId == parent.id).missions;
        }
    }
}


runner({
    typeDefs,
    resolvers
})
