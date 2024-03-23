import { defaultApi } from "$lib/store";
import { get } from "svelte/store";

/** @typedef {import("$lib/generated/models/Announcement").Announcement} Announcement*/

/** @description Load data from server and return it to the page.
 * @returns {Promise<{announcements: Array.<Announcement>}>}
 */
export async function load() {
    /** @type {import("$lib/generated").Announcement[]} */
    let announcements = [];
    try {
        announcements = await get(defaultApi).getAnnouncements();
    } catch (e) {
        console.log("Failed to fetch announcements");
        console.log(e);
    }
    return { announcements: announcements };
}

