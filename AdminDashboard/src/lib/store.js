import { readable } from "svelte/store";
import { Configuration, DefaultApi } from "./generated";

/** @type import('svelte/store').Readable<DefaultApi> */
export const defaultApi = readable(
    new DefaultApi(
        new Configuration({
            basePath: "http://localhost:8080",
            headers: {
                Authorization:
                    "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huQGdtYWlsLmNvbSIsImlhdCI6MTcxMTk5NzI5OCwiZXhwIjoxNzEyMDE1Mjk4fQ.ySAi8c-S22JgkpCW9NiBAZWGIp0qp5vXZk1yjytlS0trhsjzOxW-tqTkA9YjRLDUHaj6hNv_Nd7X1ut185rCZw",
            },
        }),
    ),
);
