import { goto } from "$app/navigation";
import { Configuration, DefaultApi } from "$lib/generated";
import Cookies from "js-cookie";
import { jwtDecode } from "jwt-decode";
import { get, writable } from "svelte/store";

/** @typedef {import("$lib/generated").UserInfo} UserInfo */

/** @type {import("svelte/store").Writable.<UserInfo | undefined>} */
export const userInfo = writable(undefined);

/** @type {import("svelte/store").Writable.<string | undefined>} */
const userId = writable(undefined);

/**
 * @returns DefaultApi
 * @param {boolean} [loginPage]
 */
export function getDefaultApi(loginPage) {
    const token = Cookies.get("StudentPortalAuthToken");

    /** @type {import("$lib/generated").HTTPHeaders} headers */
    let headers = {};

    if (!loginPage) {
        if (token !== undefined) {
            let payload = jwtDecode(token);
            let exp = payload.exp;
            userId.set(payload.sub);

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
    }

    let config = new Configuration({
        basePath: "https://localhost:8443",
        headers: headers,
    });

    let api = new DefaultApi(config);
    return api;
}

export async function getUserInfo() {
    let api = getDefaultApi();
    let userid = get(userId);
    if (userid === undefined) {
        return;
    }
    try {
        let uInfo = api.getUserInfo({ userId: userid });
        userInfo.set(await uInfo);
    } catch (e) {
        console.log("Failed to get userDetails: ");
        console.log(e);
    }
}
