<script>
    import { createEventDispatcher } from "svelte";

    let availableTags = [
        "AC",
        "Wifi",
        "Mess",
        "Room Cleaning",
        "Electic Issues",
    ];
    let selectedTags = ["complain"];
    const dispatch = createEventDispatcher();

    /**
     * @param {string} tag
     */
    function addTag(tag) {
        if (!selectedTags.includes(tag)) {
            selectedTags = [...selectedTags, tag];
            availableTags = availableTags.filter((t) => t !== tag);
            dispatch("tagsChanged", selectedTags);
        }
    }

    /**
     * @param {string} tag
     */
    function removeTag(tag) {
        selectedTags = selectedTags.filter((t) => t !== tag);
        if (!availableTags.includes(tag)) {
            availableTags = [...availableTags, tag];
            dispatch("tagsChanged", selectedTags);
        }
    }
</script>

<div class="tag-selection">
    <label for="tags-selection">Select Tags:</label>
    <div class="selected-tags">
        {#each selectedTags as tag}
            <!-- svelte-ignore a11y-click-events-have-key-events -->
            <!-- svelte-ignore a11y-no-static-element-interactions -->
            <div class="tag" on:click={() => removeTag(tag)}>
                {tag}
                <span class="remove-tag">&#10006;</span>
            </div>
        {/each}
    </div>

    <label for="available-tags">Available Tags:</label>
    <div class="available-tags">
        {#each availableTags as tag}
            <!-- svelte-ignore a11y-click-events-have-key-events -->
            <!-- svelte-ignore a11y-no-static-element-interactions -->
            <div class="tag" on:click={() => addTag(tag)}>
                {tag}
            </div>
        {/each}
    </div>
</div>

<style>
    .tag-selection {
        width: fit-content;
        margin: 20px 0px;
        display: flex;
        align-items: flex-start;
        flex-direction: column;
        flex-wrap: wrap;
    }

    .selected-tags {
        padding: 10px;
        widows: 100%;
        flex-wrap: wrap;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    label {
        font-weight: bold;
        margin-bottom: 0.4vh;
        margin-top: 2vh;
    }

    .tag {
        display: inline-block;
        margin: 10px;
        padding: 12px 14px;
        background-color: #007bff;
        color: white;
        border-radius: 20px;
        cursor: pointer;
    }

    .remove-tag {
        margin-left: 5px;
        cursor: pointer;
    }

    .available-tags {
        display: flex;
        border: 1px solid #ccc;
        border-radius: 5px;
        flex-wrap: wrap;
        padding: 10px;
    }
</style>
