import { readable } from "svelte/store";
import { Configuration, DefaultApi } from "./generated";
import Cookies from "js-cookie";

const token = Cookies.get("StudentPortalAuthToken");

/** @type import('svelte/store').Readable<DefaultApi> */
export const defaultApi = readable(
    new DefaultApi(
        new Configuration({
            basePath: "http://localhost:8080",
            headers: {
                Authorization: "Bearer " + token,
            },
        }),
    ),
);
