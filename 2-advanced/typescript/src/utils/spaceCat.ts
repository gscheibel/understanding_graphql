export class SpaceCat {
    id: number;
    name: string;
    missions?: Mission[] = null;

    constructor(id: number, name: string, missions?: Mission[]) {
        this.id = id;
        this.name = name;
        this.missions = missions;
    }
}

export class Mission {
    name: string;

    constructor(name: string) {
        this.name = name;
    }
}

export const catsAndMissions: SpaceCat[] = [
    new SpaceCat(0, "Neil Catstrong", [new Mission("ApolloMeow")]),
    new SpaceCat(1, "Buzz")
]

 