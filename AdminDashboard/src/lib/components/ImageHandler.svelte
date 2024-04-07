<script>
    /**
     * @type {string[]}
     */
    export let imageUrls;
</script>

<h2 class="main-title">Images</h2>
<div class="image-container">
    {#each imageUrls as imageUrl}
        <div class="image-display">
            <!-- svelte-ignore a11y-img-redundant-alt -->
            <img src={imageUrl} alt="Downloaded Image" />
        </div>
    {/each}
</div>

<style>
    .image-container {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        align-items: center;
        margin-top: 20px;
    }

    .image-display {
        width: 80%;
        padding: 3rem;
        border: 1px solid #ccc;
        border-radius: 5px;
        background-color: #f9f9f9;
        margin: 2rem;
    }

    .image-container img {
        max-width: 100%;
        max-height: 50vh;
        margin: 10px;
        margin: 10px auto;
        display: block;
        border: green solid 2px;
    }
</style>
