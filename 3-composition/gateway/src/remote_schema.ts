import {print} from "graphql";
import {introspectSchema, wrapSchema} from "@graphql-tools/wrap";
import fetch from "node-fetch";

export module remoteSchema {
    export const make = async (url: string) => {
        // @ts-ignore
        const executor = async ({document, variables}) => {
            const query = print(document)
            const fetchResult = await fetch(url, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({query, variables})
            })
            return fetchResult.json()
        }

        // @ts-ignore
        return wrapSchema({
            schema: await introspectSchema(executor),
            executor
        })
    };
}