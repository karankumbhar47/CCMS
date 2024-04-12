import { goto } from "$app/navigation";
import { Configuration, DefaultApi } from "$lib/generated";
import Cookies from "js-cookie";
import { jwtDecode } from "jwt-decode";

/** @returns DefaultApi */
export function getDefaultApi() {
    const token = Cookies.get("StudentPortalAuthToken");

    /** @type {import("$lib/generated").HTTPHeaders} headers */
    let headers = {};

    if (token !== undefined) {
        /* verify token */
        let exp = jwtDecode(token).exp;
        if (exp === undefined) {
            console.log("Token expiary not set, redirecting to login page");
            goto("/login");
        } else {
            exp *= 1000;
            let expiaryTime = new Date(exp);
            if (expiaryTime < new Date()) {
                console.log("Token expiared, redirecting to login page");
                goto("/login");
            }

            headers = { Authorization: "Bearer " + token };
        }
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
