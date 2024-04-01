import { get } from "svelte/store";
import { defaultApi } from "$lib/store"; // Import the defaultApi store

// Define the type for the user details
/** @typedef {import("$lib/generated/models/UsersDetail").UsersDetail} UsersDetail */

/**
 * @description Load data from server and return it to the page.
 * @returns {Promise<{ users: Array<UsersDetail> }>}
 */
export async function load() {
    /** @type {import("$lib/generated").UsersDetail[]}*/
    let users = [];
    try {
        // Fetch user details from the server using the generated OpenAPI client
        users = await get(defaultApi).usersDetail();
        // console.log(users)
    } catch (error) {
        console.error("Failed to fetch user details:", error);
    }

    return { users: users };
}
