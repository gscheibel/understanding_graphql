import {gql} from "apollo-server";
import {runner} from "./utils/server";

const typeDefs = gql`
    type Query {
        hello: String!
        cat: SpaceCat!
    }

    type SpaceCat {
        id: Int
        name: String
        mission: Mission
    }
    interface Mission {
        name: String!
    }

    enum Orbit { LOW, HIGH }

    type NormalMission implements Mission {
        name: String!
        orbit: Orbit
        foo: String
    }

    type CrazyMission implements Mission {
        name: String!
        planet: String!
        bar: Int
    }
`

interface Mission {
    name: string
    readonly type: string
}

class NormalMission implements Mission {
    name: string
    orbit: Orbit
    foo: string

    constructor(name: string, orbit: Orbit, foo?: string) {
        this.name = name
        this.orbit = orbit
        this.foo = foo
    }

    readonly type = 'NormalMission'
}

class CrazyMission implements Mission {
    name: string;
    readonly type: string = 'CrazyMission'


}

enum Orbit { LOW = "LOW", HIGH = "HIGH" }

const resolvers = {

    Query: {
        cat: () => {
            return {
                id: 1,
                name: "Buzz",
                mission: new NormalMission(
                    "CrazyThings",
                    null,
                    "foo"
                )
            }
        }
    },

    Mission: {
        __resolveType: (mission: Mission, context, info) => {
            console.table(mission);
            return mission.type
        }
    }
}


runner({
    typeDefs,
    resolvers
})
