<script>
    export let message = '';
    export let type = 'info'; 
    
    function closeAlert() {
        message = '';
    }
</script>

{#if message}
    <div class="alert" class:type>
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

    .info {
        background-color: #2196F3; /* Blue */
        color: white;
    }
    .success {
        background-color: #4CAF50; /* Green */
        color: white;
    }
    .warning {
        background-color: #ff9800; /* Orange */
        color: white;
    }
    .error {
        background-color: #f44336; /* Red */
        color: white;
    }
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
