<script>
    import ImageHandler from "$lib/components/ImageHandler.svelte";
    import TagsHandler from "$lib/components/TagsHandler.svelte";
    import { ComplainSubmitStatusEnum } from "$lib/generated/models";
    import { ComplainOverviewSeverityEnum } from "$lib/generated/models";
    import { jwtDecode } from "jwt-decode";
    import Cookies from "js-cookie";
    import { getDefaultApi } from "$lib/utils/auth";
    import resetAllImages from "$lib/components/ImageHandler.svelte"


    /**@type {ImageHandler}*/
    let uploadImage;

    /**@type {string[]}*/
    let FileIds = [];

    /** @type {string | undefined} token */
    const token = Cookies.get("StudentPortalAuthToken");
    let selectedTags = ["Complain"];
    const severityOptions = ["Low", "Medium", "High", "Critical"];
    const locationOptions = [
        "None",
        "Boys Hostel",
        "Girls Hostel",
        "Academic Area",
        "Mess",
        "Other",
    ];
    const availableTagsDefault = [
        "AC",
        "Wifi",
        "Mess",
        "Room Cleaning",
        "Electic Issues",
    ];

    let isLoading = false;
    let availableTags = availableTagsDefault;
    let complaintDescription = "";
    let selectedSeverity = "Low";
    let selectedLocation = "None";
    let locationDetails = "";
    let currentDate = "";

    if (token !== undefined) {
        console.log(jwtDecode(token).name);
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
     * convert severity string value to
     * respective Enum value
     * @param {string} severity
     */
    function severityEnum(severity) {
        if (severity === "High") {
            return ComplainOverviewSeverityEnum.High;
        } else if (severity == "Critical") {
            return ComplainOverviewSeverityEnum.Critical;
        } else if (severity == "Medium") {
            return ComplainOverviewSeverityEnum.Medium;
        } else {
            return ComplainOverviewSeverityEnum.Low;
        }
    }

    async function handleSubmit() {
        try {
            currentDate = formatDateTime(new Date());

            /**
             * @todo get complainer id from local storage
             * @todo get complainer name from local storage
             * @todo also save complain id locally if want to
             *       show the complain
             */
            const myComplaint = {
                complainerId: 12140860,
                complain: complaintDescription,
                dateTime: currentDate,
                location: locationDetails,
                complainerName: jwtDecode(token).name,
                tags: selectedTags,
                status: ComplainSubmitStatusEnum.Unseen,
                severity: severityEnum(selectedSeverity),
                fileIds: FileIds,
            };

            console.log(selectedTags);

            const requestParameters = { complainSubmit: myComplaint };
            const apiClient = getDefaultApi();
            const response = await apiClient.submitComplaint(requestParameters);
            alert("Complaint submitted successfully");
            resetAllVars();
            console.log(response);
        } catch (error) {
            console.error("Error submitting complaint:", error);
        }
    }

    function resetAllVars() {
        selectedTags = ["Complain"];
        availableTags = availableTagsDefault;
        complaintDescription = "";
        selectedSeverity = "Low";
        selectedLocation = "None";
        locationDetails = "";
        currentDate = "";
        resetAllImages();
    }

    async function submitForm() {
        console.log("start");
        isLoading = true;
        console.log(selectedTags);
        resetAllVars()
        try {
            let flag = true;
            if (selectedLocation === "None" || locationDetails.trim() === "") {
                flag = false;
            }
            if (complaintDescription === "") {
                flag = false;
            }
            if (selectedTags.length === 0) {
                flag = false;
            }

            console.log("dialog");
            if (!flag) {
                alert("Please fill all Information");
            } else {
                console.log("try");
                await uploadImage.handleSubmit();
                console.log("completed try");
                handleSubmit();
            }
        } catch (error) {
            console.log("error");
            console.error("Error handling file upload:", error);
        } finally {
            console.log("finally");
            isLoading = false;
        }
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

    <div class="severity-dropdown">
        <label for="severity">Severity:</label>
        <select id="severity" bind:value={selectedSeverity}>
            {#each severityOptions as option}
                <option value={option}>{option}</option>
            {/each}
        </select>
    </div>

    <div class="location-dropdown">
        <label for="location">Major Location:</label>
        <select id="location" bind:value={selectedLocation}>
            {#each locationOptions as option}
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

    <TagsHandler bind:selectedTags={selectedTags} bind:availableTags={availableTags}/>
    <div class="image-container">
        <ImageHandler bind:this={uploadImage} on:list={handleFileIds} />
    </div>

    <button class="submit-button" on:click={submitForm}>Submit</button>
</div>

<style>
    .image-container {
        width: 100%;
    }

    .location-dropdown {
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

    .severity-dropdown {
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
