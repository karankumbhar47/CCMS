<script>
    // Import necessary modules
    import { onMount } from "svelte";
    import { getDefaultApi } from "$lib/utils/auth";
    /**
     * @typedef {import("$lib/generated").Location} location;
     */
    /**
     * @typedef {import("$lib/generated").Category} Category;
     */
    /**
     * @type {location []  | undefined};
     */
    let locationData = [];
    /**
     * @type  {location  | undefined };
     */
    let selectedZone;
    /**
     * @type  { Category | undefined };
     */
    let selectedCategory;

    let isEditing = false;
    /**
     * @type {number | undefined}
     */
    let editedIndex;
    let newEmail = "";
    let addedemail = false;

    // Fetch location data from the server
    async function fetchLocationData() {
        try {
            locationData = await getDefaultApi().getLocation();
            console.log("Location data:", locationData);
        } catch (error) {
            console.error("Failed to fetch location data:", error);
        }
    }

    /**
     * @param {location} zone
     */
    function selectZone(zone) {
        selectedZone = zone;
        selectedCategory = undefined; // Reset selected category when zone changes
        isEditing = false; // Reset editing state
        editedIndex = undefined;
    }

    /**
     * @param {import("$lib/generated").Category | undefined} category
     */
    function selectCategory(category) {
        selectedCategory = category;
        isEditing = false; // Reset editing state
        editedIndex = undefined;
    }

    /**
     * @param {number | undefined} index
     */
    function enableEditing(index) {
        isEditing = true;
        editedIndex = index;
    }

    /**
     * @param {number} index
     * @param {string | undefined} newEmail
     */
    function updateEmail(index, newEmail) {
        if (selectedCategory?.emails && newEmail != undefined ) {
            selectedCategory.emails[index] = newEmail;
            isEditing = false;
            editedIndex = undefined;
            saveChanges();
        } else {
            console.error("No emails to update");
        }
    }

    async function saveChanges() {
        try {
            if (selectedZone) {
                await getDefaultApi().updateLocation({
                    location: selectedZone,
                });
                console.log("Location data updated successfully.");
                isEditing = false;
                editedIndex = undefined;
                addedemail = false;
            }
        } catch (error) {
            console.error("Failed to update location data:", error);
        }
    }

    /**
     * @param {number | undefined} index
     */
    async function deleteEmail(index) {
        if (index != undefined  && selectedCategory?.emails != undefined) {
            selectedCategory.emails.splice(index, 1);
            enableEditing(index);
            await saveChanges();
        } else {
            console.log;
        }
    }

    async function addNewEmail() {
        if (newEmail !== "" && selectedCategory?.emails != undefined) {
            selectedCategory.emails.push(newEmail);
            isEditing = true;
            addedemail = true;
            await saveChanges();
            newEmail = "";
        }
    }

    onMount(() => {
        fetchLocationData();
    });
</script>

<h1>Location</h1>
<div class="zone-dropdown">
    <label for="zone-select">Select ZoneName</label>
    <div class="dropdown">
        <button class="dropdown-btn">
            {#if selectedZone}
                {selectedZone.zoneName}
            {:else}
                Select ZoneName
            {/if}
            <i class="arrow down"></i>
        </button>
        <div class="dropdown-content">
            {#if locationData !=  undefined}
            {#each locationData as zone}
                <button on:click={() => selectZone(zone)}
                    >{zone.zoneName}</button
                >
            {/each}
            {/if}   
        </div>
    </div>

    {#if selectedZone !== undefined}
        <h2>Zone Name: {selectedZone.zoneName}</h2>
        <table>
            <thead>
                <tr>
                    <th>Building Name</th>
                </tr>
            </thead>
            <tbody>
                {#each selectedZone.buildings as building}
                    <tr>
                        <td>{building}</td>
                    </tr>
                {/each}
            </tbody>
        </table>

        <div class="dropdown">
            <button class="dropdown-btn">
                {#if selectedCategory}
                    {selectedCategory.name}
                {:else}
                    Select Category
                {/if}
                <i class="arrow down"></i>
            </button>
            <div class="dropdown-content">
                {#each selectedZone.categories as category}
                    <button on:click={() => selectCategory(category)}
                        >{category.name}</button
                    >
                {/each}
            </div>
        </div>

        {#if selectedCategory !== undefined}
            <h3>Selected Category: {selectedCategory.name}</h3>
            <table>
                <thead>
                    <tr>
                        <th>Emails</th>
                        <th>Action</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    {#each selectedCategory.emails as email, index}
                        <tr>
                            <td>
                                {#if isEditing && (editedIndex === index || addedemail)}
                                    <input
                                        type="text"
                                        bind:value={selectedCategory.emails[
                                            index
                                        ]}
                                    />
                                {:else}
                                    {email}
                                {/if}
                            </td>
                            <td>
                                {#if isEditing && editedIndex === index && selectedCategory !== undefined && selectedCategory.emails !== undefined}
                                    <button
                                        on:click={() =>
                                            updateEmail(
                                                index,
                                                selectedCategory?.emails[index],
                                            )}>Update</button
                                    >
                                {:else}
                                    <button
                                        on:click={() => enableEditing(index)}
                                        >Change</button
                                    >
                                {/if}
                            </td>
                            <td>
                                <button on:click={() => deleteEmail(index)}
                                    >Delete</button
                                >
                            </td>
                        </tr>
                    {/each}
                    <tr>
                        <td>
                            <input
                                type="text"
                                bind:value={newEmail}
                                placeholder="New Email"
                            />
                        </td>
                        <td colspan="2">
                            <button on:click={addNewEmail}>Add Email</button>
                        </td>
                    </tr>
                </tbody>
            </table>
            {#if isEditing}
                <button on:click={saveChanges}>Save Changes</button>
            {/if}
        {/if}
    {/if}
</div>

<style>
    .zone-dropdown {
        font-family: "Roboto", sans-serif;
        background-color: #f5f5f5;
        width: 70vw;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .dropdown {
        position: relative;
        display: inline-block;
        margin-bottom: 20px;
    }

    .dropdown-btn {
        background-color: #fff;
        color: #333;
        padding: 12px 16px;
        font-size: 16px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        display: flex;
        justify-content: space-between;
        align-items: center;
        width: 300px;
    }

    .dropdown-content {
        display: none;
        /* position: absolute; */
        background-color: #fff;
        min-width: 300px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        z-index: 1;
        border-radius: 4px;
    }

    .dropdown-content button {
        color: #333;
        padding: 12px 16px;
        text-decoration: none;
        display: block;
        text-align: left;
        border: none;
        background-color: transparent;
        cursor: pointer;
        width: 100%;
    }

    .dropdown-content button:hover {
        background-color: #f1f1f1;
    }

    .dropdown:hover .dropdown-content {
        display: block;
    }

    .arrow {
        border: solid #333;
        border-width: 0 2px 2px 0;
        display: inline-block;
        padding: 3px;
    }

    .down {
        transform: rotate(45deg);
        -webkit-transform: rotate(45deg);
    }

    label {
        display: block;
        font-weight: bold;
        margin-bottom: 8px;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 20px;
        background-color: #fff;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    th,
    td {
        padding: 12px 16px;
        border-bottom: 1px solid #ddd;
    }

    th {
        background-color: #f5f5f5;
        font-weight: 500;
    }

    input[type="text"] {
        width: 100%;
        padding: 12px 16px;
        font-size: 16px;
        margin-right: 10px;
        border: none;
        border-radius: 4px;
        background-color: #f5f5f5;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    button {
        background-color: #4caf50;
        color: white;
        padding: 12px 16px;
        /* margin-left: 150px; */
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 16px;
        transition: background-color 0.3s ease;
    }

    button:hover {
        background-color: #45a049;
    }
</style>
