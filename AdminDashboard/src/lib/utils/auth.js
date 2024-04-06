import { goto } from "$app/navigation";
import { Configuration, DefaultApi } from "$lib/generated";
import Cookies from "js-cookie";

/** @returns DefaultApi */
export function getDefaultApi() {
    const token = Cookies.get("authToken");

    /** @type {import("$lib/generated").HTTPHeaders} headers */
    let headers = {};

    if (token !== undefined) {
        headers = { Authorization: "Bearer " + token };
    } else {
        console.log("No token found, redirecting to login page");
        goto("/login");
    }

    let config = new Configuration({
        basePath: "http://localhost:8080",
        headers: headers,
    });

    let api = new DefaultApi(config);
    return api;
}
