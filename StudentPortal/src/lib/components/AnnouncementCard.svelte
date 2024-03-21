<script>
    import { onMount } from "svelte";

    /** @typedef {import("$lib/generated/models/Announcement").Announcement} Announcement*/

    /**@type {Announcement}*/
    export let announcement;

    onMount(() => {
        console.log("Loading Announcements card");
    });
</script>

<div class="card">
    <h2>{announcement.title}</h2>
    <h3>{announcement.time}</h3>
    <p>{announcement.description}</p>
</div>

<style>
    .card {
        position: relative;
        margin: 0.5em;
        padding: 0.5em 0.5em 0.5em 6em;
        border: 1px solid #eee;
        border-radius: 4px;
        box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
        min-height: 5em;
    }

    .card::after {
        clear: both;
        display: block;
    }

    h2 {
        display: inline;
        margin: 0 0 0.5em 0;
        font-size: 16px;
    }

    h3 {
        display: inline;
        margin-left: 10px;
        font-size: 12px;
    }

    p {
        margin: 0;
        font-size: 14px;
    }
</style>
