<script>
    import { createEventDispatcher } from "svelte";

    export let title = "";
    export let message = "";
    export let show = false;
    export let positiveButtonLabel = "Yes";
    export let negativeButtonLabel = "No";
    export let showPositiveButton = true;
    export let showNegativeButton = true;

    $: show == true ? console.log("showing") : console.log("not showing");

    const dispatch = createEventDispatcher();

    function handlePositive() {
        dispatch("positive");
    }

    function handleNegative() {
        dispatch("negative");
    }
</script>

{#if show}
    <div class="overlay"></div>
    <!-- Semi-transparent overlay -->
    <div class="dialog-container">
        <div class="dialog">
            <h2 class="title" style="text-align: left;">{title}</h2>
            <p>{message}</p>
            <div class="buttons" style="text-align: right;">
                {#if showPositiveButton}
                    {#if title == "Error"}
                        <button
                            on:click={handlePositive}
                            style="background-color: #DC143C;"
                            >{positiveButtonLabel}</button
                        >
                    {:else}
                        <button on:click={handlePositive}
                            >{positiveButtonLabel}</button
                        >
                    {/if}
                {/if}
                {#if showNegativeButton}
                    <button on:click={handleNegative}
                        >{negativeButtonLabel}</button
                    >
                {/if}
            </div>
        </div>
    </div>
{/if}

<style>
    .overlay {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent black */
        z-index: 999; /* Ensure the overlay is above other elements */
    }

    .title {
        margin: 0;
    }

    /* Styles for the dialog container */
    .dialog-container {
        position: fixed;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        background-color: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.2);
        text-align: center;
        z-index: 1000; /* Ensure the dialog is above the overlay */
    }

    /* Styles for the dialog box */
    .dialog {
        text-align: center;
    }

    /* Styles for the buttons */
    .buttons {
        margin-top: 20px;
    }

    .buttons button {
        padding: 10px 20px;
        margin: 0 10px;
        cursor: pointer;
        border: none;
        border-radius: 5px;
        background-color: #007bff;
        color: white;
        margin: 0 10;
    }
</style>
