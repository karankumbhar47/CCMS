<script>
    export let message = "";
    export let show = false;
    export let signal = () => {};

    function handleCancel() {
        show = false;
    }

    function handleOK() {
        show = false;
        signal();
    }
</script>

{#if show}
    <div class="modal">
        <div class="modal-content">
            <p>{message}</p>
            <div class="button-container">
                <button on:click={handleCancel}>Cancel</button>
                <button on:click={handleOK}>OK</button>
            </div>
        </div>
    </div>
{/if}

<style>
    .modal {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 999;
    }

    .modal-content {
        background-color: white;
        padding: 20px;
        border-radius: 5px;
        text-align: center;
    }

    .button-container {
        margin-top: 20px;
    }

    button {
        margin: 0 10px;
        padding: 8px 16px;
        cursor: pointer;
    }
</style>
