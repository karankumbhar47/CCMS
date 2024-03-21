<script>
    import { createEventDispatcher } from 'svelte';

    let availableTags = ["AC", "Wifi", "Mess", "Room Cleaning", "Electic Issues"];
    let selectedTags = ["complain"];
    const dispatch = createEventDispatcher();


    function addTag(tag) {
        if (!selectedTags.includes(tag)) {
            selectedTags = [...selectedTags, tag]; // Create a new array with the added tag
            availableTags = availableTags.filter((t) => t !== tag);
            dispatch('tagsChanged', selectedTags); // Emit an event with the updated selected tags list
    
        }
    }

    function removeTag(tag) {
        selectedTags = selectedTags.filter((t) => t !== tag);
        if (!availableTags.includes(tag)) {
            availableTags = [...availableTags, tag]; // Create a new array with the removed tag
            dispatch('tagsChanged', selectedTags); // Emit an event with the updated selected tags list
  
        }


    }
</script>

<div class="tag-selection">
    <label for="tags-selection">Select Tags:</label>
    <div class="selected-tags">
        {#each selectedTags as tag}
            <div class="tag" on:click={() => removeTag(tag)}>
                {tag}
                <span class="remove-tag">&#10006;</span>
            </div>
        {/each}
    </div>

    <label for="available-tags">Available Tags:</label>
    <div class="available-tags">
        {#each availableTags as tag}
            <div class="tag" on:click={() => addTag(tag)}>
                {tag}
            </div>
        {/each}
    </div>
</div>

<style>
    /* Style for the tag selection container */
    .tag-selection {
        width: fit-content;
        margin: 20px 0px;
        display: flex;
        align-items: flex-start;
        flex-direction: column;
        flex-wrap: wrap;
        /* background-color: #007bff; */
    }

    /* Style for the selected tags container */
    .selected-tags {
        padding: 10px;
        widows: 100%;
        flex-wrap: wrap; /* Allow the selected tags to wrap to the next line */
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    label {
        font-weight: bold;
        margin-bottom: 0.4vh;
        margin-top: 2vh;
    }

    /* Style for each selected tag */
    .tag {
        display: inline-block;
        margin: 10px;
        padding: 12px 14px;
        background-color: #007bff;
        color: white;
        border-radius: 20px;
        cursor: pointer;
    }

    /* Style for the cross mark to remove the tag */
    .remove-tag {
        margin-left: 5px;
        cursor: pointer;
    }

    /* Style for the available tags container */
    .available-tags {
        display: flex;
        border: 1px solid #ccc;
        border-radius: 5px;
        /* background-color: yellow; */
        flex-wrap: wrap;
        padding: 10px;
    }
</style>
