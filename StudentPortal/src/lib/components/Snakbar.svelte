<!-- WarningSnackbar.svelte -->
<script>
    import { writable } from "svelte/store";

    export let message = "";
    export let duration = 3000; // Default duration for the snackbar to be displayed
    export let show = writable(false);
    export let backgroundColor = "#ff9800";

    /**
     * @type {number | undefined}
     */
    let timeout;

    export function showSnackbar() {
        show.set(true);
        clearTimeout(timeout);
        timeout = setTimeout(() => {
            show.set(false);
        }, duration);
    }

    function closeSnackbar() {
        show.set(false);
        clearTimeout(timeout);
    }
</script>

<div
    class="snackbar"
    class:show={$show}
    style="background-color: {backgroundColor};"
>
    <div class="message">{message}</div>
    <button class="close" on:click={closeSnackbar}>&times;</button>
</div>

<style>
    .snackbar {
        position: fixed;
        bottom: 20px;
        left: 50%;
        transform: translateX(-50%);
        color: #fff;
        padding: 10px 20px;
        border-radius: 5px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        display: none;
        z-index: 1000;
    }

    .snackbar.show {
        display: flex;
        align-items: center;
    }

    .message {
        flex: 1;
        margin-right: 10px;
    }

    .close {
        background: none;
        border: none;
        color: inherit;
        font-size: 20px;
        cursor: pointer;
    }
</style>
