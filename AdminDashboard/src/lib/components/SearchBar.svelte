<script>
    let searchTerm = "";

    function handleSearch() {
        console.log("Search term:", searchTerm);
    }
</script>

<div class="search-bar">
    <input
        type="text"
        placeholder="Search..."
        bind:value={searchTerm}
        class="search-input"
    />
    <button on:click={handleSearch} class="search-button">Search</button>
</div>

<style>
    .search-bar {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 100vh;
        margin-bottom: 20px;
    }

    .search-input {
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        flex-grow: 1;
        margin-right: 10px;
        font-size: 16px;
    }

    .search-button {
        padding: 10px 20px;
        background-color: #007bff;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 16px;
        transition: background-color 0.3s;
    }

    .search-button:hover {
        background-color: #0056b3;
    }
</style>
