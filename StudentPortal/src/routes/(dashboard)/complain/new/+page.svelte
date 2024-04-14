<script>
    import ImageHandler from "$lib/components/ImageHandler.svelte";
    import Cookies from "js-cookie";
    import { getDefaultApi, userInfo } from "$lib/utils/auth";
    import { onMount } from "svelte";
    import zonesData from "$lib/data/location.json";
    import {
        ComplaintInfoPriorityEnum,
        ComplaintInfoStatusEnum,
        ComplaintInfoLevelEnum,
    } from "$lib/generated/models";
    import { get } from "svelte/store";

    /**@type {ImageHandler}*/
    let uploadImage;

    /**@type {string[]}*/
    let FileIds = [];

    /** @type {string | undefined} token */
    const token = Cookies.get("StudentPortalAuthToken");

    let priorityOptions = ["Normal", "Urgent", "Medium"];

    /**@type {string[]}*/
    let zones = [];

    /**@type {string[]}*/
    let buildingNames = [];

    /**@type {string[]}*/
    let complaintCriteria = [];

    let isLoading = false;
    let complaintDescription = "";
    let selectedPriority = "Normal";
    let selectedZone = "";
    let selectedBuilding = "";
    let selectedCriteria = "";
    let locationDetails = "";
    let currentDate = "";

    onMount(async () => {
        zones = zonesData.zone.map((zone) => zone.zoneName);
        selectedZone = zones[0];
        updateBuildingNamesAndCriteria();
    });

    function updateBuildingNamesAndCriteria() {
        const selectedZoneObject = zonesData.zone.find(
            (zone) => zone.zoneName === selectedZone,
        );
        if (selectedZoneObject) {
            buildingNames = selectedZoneObject.buildings;
            complaintCriteria = selectedZoneObject.categories.map(
                (category) => category.name,
            );
        } else {
            buildingNames = [];
            complaintCriteria = [];
        }
        selectedBuilding = buildingNames[0];
        selectedCriteria = complaintCriteria[0];
    }

    /** @param {string} selectedZone */
    function handleZoneChange(selectedZone) {
        console.log(selectedZone);
        updateBuildingNamesAndCriteria();
    }

    /** @param {{ detail: string[]; }} event */
    function handleFileIds(event) {
        FileIds = event.detail;
        console.log("fileids in main ", FileIds);
    }

    /** @param {string | number | Date} date */
    function formatDateTime(date) {
        const options = {
            year: "numeric",
            month: "2-digit",
            day: "2-digit",
            hour: "2-digit",
            minute: "2-digit",
            timeZoneName: "short",
        };

        const dateString = new Date(date).toLocaleString("en-US", options);
        return dateString.replace(/\//g, "-").replace(",", "");
    }

    /**
     * convert priority string value to
     * respective Enum value
     * @param {string} priority
     */
    function priorityEnum(priority) {
        if (priority === "Urgent") {
            return ComplaintInfoPriorityEnum.Urgent;
        } else if (priority == "Medium") {
            return ComplaintInfoPriorityEnum.Medium;
        } else {
            return ComplaintInfoPriorityEnum.Normal;
        }
    }

    async function submitForm() {
        isLoading = true;
        try {
            let flag = true;
            if (complaintDescription === "") {
                flag = false;
            }

            if (!flag) {
                alert("Please fill all Information");
            } else {
                console.log("try");
                await uploadImage.handleSubmit();
                handleSubmit();
            }
        } catch (error) {
            console.error("Error handling file upload:", error);
        } finally {
            isLoading = false;
        }
    }

    async function handleSubmit() {
        currentDate = formatDateTime(new Date());
        const apiClient = getDefaultApi();

        /** @type {string | undefined} */
        let userId = get(userInfo)?.userId;
        if(userId === undefined){
            return;
        }

        /**
         * @todo get complainer id from local storage
         * @todo get complainer name from local storage
         * @todo also save complain id locally if want to
         *       show the complain
         */
        const complaintDetails = {
            complainerId: userId,
            description: complaintDescription,
            registrationDate: currentDate,
            resolutionDate: "",
            remarkByUser: "",
            remarkByMaintainer: "",
            buildingName: selectedBuilding,
            locationDetails: locationDetails,
            attachmentIds: FileIds,
            closureAttachmentIds: [],
            zone: selectedZone,
            priority: priorityEnum(selectedPriority),
            status: ComplaintInfoStatusEnum.Open,
            level: ComplaintInfoLevelEnum.L1,
            complaintCriteria: selectedCriteria,
        };

        try {
            const response = await apiClient.submitComplaint({
                complaintInfo: complaintInfo,
            });
            alert("Complaint submitted successfully");
            resetAllVars();
            console.log(response);
        } catch (error) {
            console.error("Error submitting complaint:", error);
        }
    }

    function resetAllVars() {
        complaintDescription = "";
        locationDetails = "";
        currentDate = "";
        selectedPriority = "Normal";
        uploadImage.resetAllImages();
    }
</script>

<div>
    {#if isLoading}
        <div>Loading...</div>
    {/if}
    <div class="complain-description-box">
        <label for="complaint-description">Complaint Description:</label>
        <div class="complain-text">
            <textarea
                id="complaint-description"
                rows="4"
                cols="50"
                bind:value={complaintDescription}
            ></textarea>
        </div>
    </div>

    <div class="priority-dropdown">
        <label for="priority">Priority :</label>
        <select id="priority" bind:value={selectedPriority}>
            {#each priorityOptions as option}
                <option value={option}>{option}</option>
            {/each}
        </select>
    </div>

    <div class="zone-dropdown">
        <label for="zone">Zone :</label>
        <select
            id="zone"
            bind:value={selectedZone}
            on:change={(e) => handleZoneChange(e.target.value)}
        >
            {#each zones as option}
                <option value={option}>{option}</option>
            {/each}
        </select>
    </div>

    <div class="building-dropdown">
        <label for="building">Area Name:</label>
        <select id="building" bind:value={selectedBuilding}>
            {#each buildingNames as option}
                <option value={option}>{option}</option>
            {/each}
        </select>
    </div>

    <div class="category-dropdown">
        <label for="category">Category :</label>
        <select id="category" bind:value={selectedCriteria}>
            {#each complaintCriteria as option}
                <option value={option}>{option}</option>
            {/each}
        </select>
    </div>

    <div class="location-details">
        <label for="location-details">Location Details:</label>
        <div class="location-text">
            <textarea id="location-details" bind:value={locationDetails}
            ></textarea>
        </div>
    </div>

    <!-- <TagsHandler bind:selectedTags bind:availableTags /> -->
    <div class="image-container">
        <ImageHandler bind:this={uploadImage} on:list={handleFileIds} />
    </div>

    <button class="submit-button" on:click={submitForm}>Submit</button>
</div>

<style>
    .image-container {
        width: 100%;
    }

    .zone-dropdown {
        margin-bottom: 20px;
    }

    label {
        font-weight: bold;
    }

    select {
        padding: 8px;
        border: 1px solid #ccc;
        width: 100%;
    }

    textarea {
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 5px;
        width: 100%;
        height: 80%;
    }

    .complain-text {
        height: 30vh;
    }

    .location-text {
        width: 40vw;
        height: 10vh;
    }

    .submit-button {
        padding: 10px 20px;
        background-color: #007bff;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        margin: 20px 0;
    }

    .priority-dropdown {
        margin-bottom: 20px;
    }

    label {
        font-weight: bold;
    }

    select {
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        width: 40vw;
        display: block;
        margin-top: 0.7rem;
    }

    .complain-description-box {
        width: 40vw;
    }

    label {
        margin-bottom: 1.5rem;
        font-weight: bold;
    }

    textarea {
        width: 100%;
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 5px;
        resize: vertical;
    }
</style>
