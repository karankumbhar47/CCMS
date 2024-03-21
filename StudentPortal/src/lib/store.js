import { readable } from "svelte/store";
import { Configuration, DefaultApi } from "./generated";

/** @type import('svelte/store').Readable<DefaultApi> */
export const defaultApi = readable(
    new DefaultApi(new Configuration({ basePath: "http://localhost:8080" })),
);
