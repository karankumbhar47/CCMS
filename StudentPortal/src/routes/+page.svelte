<!-- https://github.com/sveltejs/svelte-virtual-list -->

<script>
    import AnnouncementCard from "$lib/components/AnnouncementCard.svelte";
    import { onMount } from "svelte";

    /** @typedef {import("$lib/generated/models/Announcement").Announcement} Announcement*/

    /** @type {{announcements: Array.<Announcement>}} */
    export let data;

    onMount(() => {
        console.log(data);
    });
</script>

<h1>Announcements</h1>
<div class="container">
    {#each data.announcements as announcement}
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
