<script>
    export let message = "";
    export let type = "info";

    function closeAlert() {
        message = "";
    }
</script>

{#if message}
    <div class="alert" class:type>
        <!-- svelte-ignore a11y-click-events-have-key-events -->
        <!-- svelte-ignore a11y-no-static-element-interactions -->
        <span class="close" on:click={closeAlert}>&times;</span>
        <p>{message}</p>
    </div>
{/if}

<style>
    .alert {
        padding: 15px;
        border-radius: 4px;
        margin-bottom: 20px;
        font-size: 16px;
    }

    /* .info {
        background-color: #2196f3;
        color: white;
    }
    .success {
        background-color: #4caf50;
        color: white;
    }
    .warning {
        background-color: #ff9800;
        color: white;
    }
    .error {
        background-color: #f44336;
        color: white;
    } */
    .close {
        float: right;
        font-size: 20px;
        font-weight: bold;
        cursor: pointer;
    }
    .close:hover {
        color: black;
    }
</style>
