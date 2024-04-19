<script>
    import { getDefaultApi, userInfo } from "$lib/utils/auth";
    import { onMount } from "svelte";
    import zonesData from "$lib/data/location.json";
    import {
        ComplaintInfoStatusEnum,
        ComplaintInfoLevelEnum,
        ComplaintInfoPriorityEnum,
    } from "$lib/generated/models";
    import { get } from "svelte/store";
    import SimpleDisplay from "$lib/components/SimpleDisplay.svelte";
    import { UploadFileUserTypeEnum } from "$lib/generated";
    import WarningSnackbar from "$lib/components/Snakbar.svelte";
    import Dialog from "$lib/components/Dialog.svelte";

    let titleAlert = "";
    let messageAlert = "";
    let errorColor = "#DC143C";
    let warningColor = "#ff9800";
    let color = warningColor;
    let showNegativeButton = true;

    /**
     * @type {string[]}
     */
    let fileIds = [];

    /** @type {string}*/
    const title = "Selected files";

    function addImage() {
        let element = document.getElementById("fileInput");
        if (element) element.click();
    }

    /**@type {string[]}*/
    let zones = [];

    /**@type {string[]}*/
    let buildingNames = [];

    /**@type {string[]}*/
    let complaintCriteria = [];

    let isLoading = false;
    let complaintDescription = "";
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

    function handleZoneChange() {
        updateBuildingNamesAndCriteria();
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

    /** @type {WarningSnackbar} */
    let warningSnackBar;

    async function submitForm() {
        try {
            let flag = true;
            if (complaintDescription === "") {
                flag = false;
            }

            if (!flag) {
                messageInfo = "Please fill all Information";
                color = warningColor;
                warningSnackBar.showSnackbar();
            } else {
                messageAlert = "Are you sure, you want to submit";
                titleAlert = "Confirm";
                showDialog = true;
            }
        } catch (error) {
            messageInfo = "Invalid Information filled";
            color = errorColor;
            warningSnackBar.showSnackbar();
            console.error("Error handling file upload:", error);
        }
    }

    async function handleSubmit() {
        isLoading = true;
        currentDate = formatDateTime(new Date());

        /** @type {string | undefined} */
        let userId = get(userInfo)?.userId;
        if (userId === undefined) {
            return;
        }

        try {
            for (let i = 0; i < boxes.length; i++) {
                if (boxes[i].selectedFile) {
                    const { selectedFile } = boxes[i];
                    if (selectedFile) {
                        let fileId = await getDefaultApi().uploadFile({
                            userType: UploadFileUserTypeEnum.User,
                            imageFile: selectedFile,
                        });
                        fileIds = [...fileIds, fileId];
                    }
                }
            }

            const complaintInfo = {
                complainerId: userId,
                description: complaintDescription,
                registrationDate: currentDate,
                resolutionDate: "",
                remarkByUser: "",
                remarkByMaintainer: "",
                buildingName: selectedBuilding,
                locationDetails: locationDetails,
                attachmentIds: fileIds,
                closureAttachmentIds: [],
                zone: selectedZone,
                priority: ComplaintInfoPriorityEnum.Normal,
                status: ComplaintInfoStatusEnum.Open,
                level: ComplaintInfoLevelEnum.L1,
                complaintCriteria: selectedCriteria,
            };
            await getDefaultApi().submitComplaint({
                complaintInfo: complaintInfo,
            });
            titleAlert = "Success";
            messageAlert = "Complaint submitted successfully";
            showNegativeButton = false;
            showDialog = true;
            resetAllVars();
            isLoading = false;
        } catch (error) {
            titleAlert = "Error";
            messageAlert = "Error While submitting complaint";
            showNegativeButton = false;
            showDialog = true;
            console.error("Error submitting complaint", error);
            isLoading = false;
        }
    }

    function resetAllVars() {
        complaintDescription = "";
        locationDetails = "";
        currentDate = "";
        selectedAttachments = [];
        boxes = [];
    }

    let MAX_IMAGES = 5;
    const MAX_FILE_SIZE_MB = 2;
    let size = 0;
    let messageInfo = "";

    /** @type {Array.<{selectedFile: null | File; fileId: string | null; imageUrl: string | null}>} boxes*/
    let boxes = [];
    /**
     * @typedef {Object} ImageData
     * @property {string} imageUrl
     * @property {string} fileId
     */
    /** @type {Array<ImageData>}*/
    let selectedAttachments = [];

    /** @param {{ target: { files: Array<File>; }; }} event */
    function handleFileSelect(event) {
        const files = event.target.files;

        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            let selectedFileNum = ++size;

            if (!file.type.startsWith("image/")) {
                alert("Please select only images.");
                continue;
            }
            if (file.size > MAX_FILE_SIZE_MB * 1024 * 1024) {
                alert(`File size exceeds ${MAX_FILE_SIZE_MB}MB.`);
                continue;
            }
            if (selectedFileNum >= MAX_IMAGES + 1) {
                messageInfo = "Maximum 5 images allowed.";
                color = warningColor;
                warningSnackBar.showSnackbar();
                break;
            }
            const imageUrl = URL.createObjectURL(file);
            let fileName = "File_" + selectedFileNum;
            boxes = [
                ...boxes,
                { imageUrl: imageUrl, selectedFile: file, fileId: fileName },
            ];
            selectedAttachments = [
                ...selectedAttachments,
                { imageUrl: imageUrl, fileId: fileName },
            ];
        }
    }

    let showDialog = false;

    function handlePositive() {
        console.log(showNegativeButton);
        if (showNegativeButton) {
            handleSubmit();
        } else {
            showNegativeButton = true;
        }
        showDialog = false;
    }

    function handleNegative() {
        showDialog = false;
    }
</script>

<div>
    <div class="main-label-container">
        <div class="label-container">
            <!-- svelte-ignore a11y-label-has-associated-control -->
            <label class="well-styled-label">New Complaint</label>
        </div>
    </div>

    {#if isLoading}
        <div class="overlay">
            <div class="loading-container">
                <div class="loading-dialog">
                    <div class="loading-spinner">
                        <!-- <CircularProgress
                            indeterminate
                            style="height: 32px; width: 32px;"
                        /> -->
                    </div>
                </div>
            </div>
        </div>
    {/if}

    <Dialog
        title={titleAlert}
        message={messageAlert}
        show={showDialog}
        positiveButtonLabel="Ok"
        negativeButtonLabel="Cancel"
        on:positive={handlePositive}
        on:negative={handleNegative}
        bind:showNegativeButton
    />

    <WarningSnackbar
        bind:backgroundColor={color}
        bind:this={warningSnackBar}
        message={messageInfo}
        duration={4000}
    />

    <div class="container">
        <div class="dropdowns">
            <div class="form-field">
                <label for="zone">Zone:</label>
                <select
                    id="zone"
                    bind:value={selectedZone}
                    on:change={handleZoneChange}
                >
                    {#each zones as option}
                        <option value={option}>{option}</option>
                    {/each}
                </select>
            </div>

            <div class="form-field">
                <label for="building">Area Name:</label>
                <select id="building" bind:value={selectedBuilding}>
                    {#each buildingNames as option}
                        <option value={option}>{option}</option>
                    {/each}
                </select>
            </div>

            <div class="form-field">
                <label for="category">Category:</label>
                <select id="category" bind:value={selectedCriteria}>
                    {#each complaintCriteria as option}
                        <option value={option}>{option}</option>
                    {/each}
                </select>
            </div>
        </div>

        <div class="textareas">
            <div class="form-field">
                <label for="location-details">Location Details:</label>
                <textarea id="location-details" bind:value={locationDetails}
                ></textarea>
            </div>

            <div class="form-field">
                <label for="complaint-description">Complaint Description:</label
                >
                <textarea
                    id="complaint-description"
                    rows="5"
                    cols="50"
                    bind:value={complaintDescription}
                ></textarea>
            </div>
        </div>
    </div>

    <input
        type="file"
        id="fileInput"
        accept="image/*"
        style="display: none;"
        multiple
        on:change={handleFileSelect}
    />

    <div class="content-container">
        <button class="add-button" on:click={addImage}>Add Image</button>
        <button class="submit-button" on:click={submitForm}>Submit</button>
    </div>

    {#if selectedAttachments.length > 0}
        <SimpleDisplay {title} fileList={selectedAttachments} />
    {/if}

    <!-- .loading-spinner {
        display: flex;
        justify-content: center;
    } -->
</div>

<style lang="scss">
    .main-label-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        margin-bottom: 2rem;
        /* Add any other styling for the container */
    }

    .label-container {
        margin-top: 20px; /* Adjust margin as needed */
        /* No fixed position */
    }

    .well-styled-label {
        background-color: #007bff; /* Background color */
        color: white; /* Text color */
        padding: 10px 20px; /* Padding */
        border-radius: 5px; /* Rounded corners */
        font-size: 18px; /* Font size */
        font-weight: bold; /* Font weight */
        text-align: center; /* Center align text */
        box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.2); /* Box shadow */
    }
    .overlay {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        z-index: 1000;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .loading-container {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .loading-dialog {
        background-color: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.2);
    }

    .loading-spinner {
        border: 4px solid rgba(0, 0, 0, 0.1);
        border-left-color: #007bff;
        border-radius: 50%;
        width: 32px;
        height: 32px;
        animation: spin 1s linear infinite;
    }

    @keyframes spin {
        to {
            transform: rotate(360deg);
        }
    }

    .content-container {
        display: flex;
        flex-direction: column;
        height: 100%;
    }

    button {
        align-self: center;
        margin-top: auto;
        padding: 10px 20px;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        margin: 20px 0;
    }

    .submit-button {
        background-color: #007bff;
    }

    .add-button {
        background-color: #1d5396;
        padding: 15px 20px;
    }

    .dropdowns {
        margin: 1rem;
        display: flex;
        flex-direction: column;
        flex: 1;
    }

    .textareas {
        margin: 1rem;
        display: flex;
        flex-direction: column;
        flex: 1;
    }

    select,
    textarea {
        width: 100%;
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 5px;
        resize: vertical;
    }

    .container {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        align-items: center; /* Center align items horizontally */
        justify-content: center; /* Center align items vertically */
        height: 100%;
    }

    .dropdowns,
    .textareas {
        margin: 1rem 2rem; /* Center-align these components horizontally */
        width: 40vw;
        min-width: 300px; /* Set a minimum width for the components */
    }

    @media (max-width: 700px) {
        .dropdowns,
        .textareas {
            width: 80vw; /* Set width to full viewport width on smaller screens */
        }
    }
    .form-field {
        margin-bottom: 1rem;
        margin-top: 1rem;
    }

    label {
        font-weight: bold;
        margin-top: 1rem;
    }

    select {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        background-color: #f8f9fa;
        font-size: 16px;
        color: #333;
    }
</style>
