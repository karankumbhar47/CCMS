<script>
    /**
     * @type {string[]}
     */
    let selectedImages = [];

    /**
     * @param {{ target: { files: any; }; }} event
     */
    function handleFileInputChange(event) {
        const files = event.target.files;
        for (let i = 0; i < files.length; i++) {
            if (selectedImages.length < 5) {
                selectedImages.push(URL.createObjectURL(files[i]));
            } else {
                alert("Maximum 5 images can be selected.");
                break;
            }
        }
    }

    /**
     * @param {number} index
     */
    function removeImage(index) {
        selectedImages.splice(index, 1);
    }
</script>

<div class="image-upload">
    <!-- Input field for uploading images -->
    <input
        type="file"
        accept="image/*"
        multiple
        onchange={handleFileInputChange}
    />

    <!-- Display selected images -->
    {#if selectedImages.length > 0}
        <div class="selected-images">
            {#each selectedImages as image, index}
                <div class="image-container">
                    <img src={image} alt="Selected Image" />
                    <button
                        class="remove-button"
                        on:click={() => removeImage(index)}>Remove</button
                    >
                </div>
            {/each}
        </div>
    {/if}
</div>

<style>
    .image-upload {
        margin-bottom: 20px;
    }

    .selected-images {
        display: flex;
        flex-wrap: wrap;
        margin-top: 10px;
    }

    .image-container {
        margin-right: 10px;
        margin-bottom: 10px;
        position: relative;
    }

    .remove-button {
        position: absolute;
        top: 5px;
        right: 5px;
        padding: 5px;
        background-color: #dc3545;
        color: white;
        border: none;
        border-radius: 50%;
        cursor: pointer;
    }

    img {
        max-width: 100px;
        max-height: 100px;
    }
</style>
