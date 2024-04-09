<script>
    import { getDefaultApi } from "$lib/utils/auth";
    import AnnouncementCard from "$lib/components/AnnouncementCard.svelte";
    import { onMount } from "svelte";

    /** @typedef {import("$lib/generated/models/Announcement").Announcement} Announcement*/

    /** @type {Array.<Announcement>} announcements */
    let announcements = [];

    onMount(async () => {
        try {
            let api = getDefaultApi();
            console.log("got new getDefaultApi");
            announcements = await api.getAnnouncements();
        } catch (e) {
            console.log("Failed to fetch announcements");
        }
    });
</script>

<h1>Announcements</h1>
<div class="container">
    {#each announcements as announcement}
        <AnnouncementCard {announcement} />
    {/each}
</div>

<style>
    .container {
        border-top: 1px solid #333;
        border-bottom: 1px solid #333;
        min-height: 200px;
        height: calc(100vh - 15em);
    }
</style>
