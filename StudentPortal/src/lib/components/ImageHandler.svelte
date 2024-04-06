<script>
    import { getDefaultApi } from "$lib/utils/auth";
    import { createEventDispatcher } from "svelte";

    /**
     * @type {string | null}
     */
    let fileId = null;

    /**
     * @type {string[]}
     */
    let fileIds = [];
    const MAX_BOXES = 5;
    const dispatch = createEventDispatcher();

    /** @type {Array.<{selectedFile: File | null; fileId: string | null; imageUrl: string | null}>} */
    let boxes = Array.from({ length: MAX_BOXES }, () => ({
        selectedFile: null,
        fileId: null,
        imageUrl: null,
    }));

    /**
     * @param {number} index
     */
    async function handleFileUpload(index) {
        console.log(index);
        const { selectedFile } = boxes[index];
        if (selectedFile) {
            try {
                const response = await getDefaultApi().uploadFile({
                    body: selectedFile,
                });
                const id = response;
                boxes[index].fileId = id;
                fileIds[index] = id;
                dispatch("list", fileIds);
                console.log("File uploaded successfully. File ID:", fileId);
            } catch (error) {
                console.error("Error uploading image:", error);
            }
        }
    }

    /**
     * @param {{target: {files: FileList;};}} event
     * @param {number} index
     */
    function handleFileChange(index, event) {
        const selectedFile = event.target.files[0];
        const imageUrl = URL.createObjectURL(selectedFile);
        boxes[index] = { selectedFile, imageUrl, fileId: null };
    }

    /**
     * @param {number} index
     */
    function removeImage(index) {
        boxes[index] = { selectedFile: null, imageUrl: null, fileId: null };
    }

    async function handleSubmit() {
        fileIds = [];
        for (let i = 0; i < MAX_BOXES; i++) {
            if (boxes[i].selectedFile) {
                console.log("Uploading file at index:", i);
                await handleFileUpload(i);
            }
        }
    }

    export { handleSubmit };
</script>

<div class="box-container">
    {#each boxes as box, index (index)}
        <div class="box">
            <input
                class="input-taker"
                type="file"
                accept="image/*"
                on:change={() => handleFileChange(index, event)}
            />
            {#if box.imageUrl}
                <div class="image-container">
                    <!-- svelte-ignore a11y-img-redundant-alt -->
                    <img src={box.imageUrl} alt="Selected Image" />
                    <button on:click={() => removeImage(index)}>Remove</button>
                </div>
            {/if}
        </div>
    {/each}
</div>

{#if fileId}
    <p>File uploaded successfully. File ID: {fileId}</p>
{/if}

<style>
    .box-container {
        display: flex;
        justify-content: space-around;
        align-items: center;
        flex-wrap: wrap;
        height: fit-content;
    }
    .box {
        position: relative;
        width: 30vw;
        height: 40vh;
        margin: 2rem;
        border: 1px solid black;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }
    .image-container {
        position: relative;
        width: 90%;
        height: 90%;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    img {
        max-width: 100%;
        max-height: 100%;
        object-fit: contain;
        z-index: 0;
        border: 2px greenyellow solid;
    }
    button {
        z-index: 100;
        position: absolute;
        bottom: 0;
        right: 0;
    }
</style>
