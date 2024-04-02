<script>
    import DemoImage from "$lib/components/DemoImage.svelte";
    import TagsHandler from "$lib/components/TagsHandler.svelte";
    import { DefaultApi } from "$lib/generated";
    import { ComplainSubmitStatusEnum } from "$lib/generated/models";
    import { ComplainOverviewSeverityEnum } from "$lib/generated/models";

    /**
     * @type {DemoImage}
     */
    let uploadImage;

    /**
     * @type {any[]}
     */
    let FileIds = [];
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

    let complaintDescription = "";
    let selectedSeverity = "Low";
    let selectedLocation = "None";
    let locationDetails = "";
    let currentDate = "";

    /**
     * Taking tags array from TagsHandler component
     * @param {{ detail: string[]; }} event
     */
    function handleTagsChanged(event) {
        selectedTags = event.detail;
    }

    /**
     * @param {{ detail: any[]; }} event
     */
    function handleFileIds(event) {
        FileIds = event.detail;
    }

    /**
     * formatting date in required format
     * @param {string | number | Date} date
     */
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
                complainerName: "Karan Kumbhar",
                tags: selectedTags,
                status: ComplainSubmitStatusEnum.Unseen,
                severity: severityEnum(selectedSeverity),
                fileIds: FileIds,
            };

            console.log(FileIds)
            const requestParameters = { complainSubmit: myComplaint };
            const apiClient = new DefaultApi();
            const response = await apiClient.submitComplaint(requestParameters);
            alert("Complaint submitted successfully");
        } catch (error) {
            console.error("Error submitting complaint:", error);
        }
    }

    function submitForm() {
        uploadImage.handleSubmit()
        console.log(uploadImage.fileIds)
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

        if (!flag) {
            alert("Please fill all Information");
        } else {
            handleSubmit();
        }
    }
</script>

<div class="complain-description-box">
    <label for="complaint-description">Complaint Description:</label>
    <textarea
        id="complaint-description"
        rows="4"
        cols="50"
        bind:value={complaintDescription}
    ></textarea>
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
    <textarea id="location-details" bind:value={locationDetails}></textarea>
</div>

<TagsHandler on:tagsChanged={handleTagsChanged} />
<!-- <DemoImage bind:this={uploadImage} on:list={handleFileIds} /> -->

<button class="submit-button" on:click={submitForm}>Submit</button>

<style>
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
        height: 100px;
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
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 5px;
        width: 100%;
    }

    .complain-description-box {
        margin-bottom: 20px;
    }

    label {
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
