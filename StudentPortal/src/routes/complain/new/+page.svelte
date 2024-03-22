<script>
    import ImageHandler from "$lib/components/ImageHandler.svelte";
    import TagsHandler from "$lib/components/TagsHandler.svelte";
    import { DefaultApi } from "$lib/generated";
    import { ComplainSubmitStatusEnum } from '$lib/generated/models'; // Import the enum type
    import { ComplainOverviewSeverityEnum } from '$lib/generated/models'; // Import the enum type


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

    let complaintDescription =
        "Room Cleaning Not done from 5-6 days. Also No single worker came to our room after writing in complain register ";
    let selectedSeverity = "Low";
    let selectedLocation = "Boys Hostel";
    let locationDetails = "Room E308";
    let currentDate = "";

    function handleTagsChanged(event) {
        selectedTags = event.detail;
    }

    function formatDateTime(date) {
        const options = {
            year: "numeric",
            month: "long",
            day: "numeric",
            hour: "2-digit",
            minute: "2-digit",
        };
        return new Date(date).toLocaleString("en-US", options).replace(",", "");
    }

    function severityEnum(severity){
        if(severity==="High"){
            return ComplainOverviewSeverityEnum.High;
        }
        else if(severity=="Critical"){
            return ComplainOverviewSeverityEnum.Critical;
        }
        else if(severity=="Medium"){
            return ComplainOverviewSeverityEnum.Medium;
        }
        else{
            return ComplainOverviewSeverityEnum.Low;
        }
    }

    async function handleSubmit() {
        try {
            const myComplaint = {
                complainerId: 12140860,
                complain: complaintDescription,
                dateTime: currentDate,
                location: locationDetails,
                complainerName: "Karan Kumbhar",
                tags: selectedTags,
                status: ComplainSubmitStatusEnum.Unseen,
                severity: severityEnum(selectedSeverity)
            };

            console.log(myComplaint);
            const apiClient = new DefaultApi();
            const requestParameters = { complainSubmit: myComplaint };
            const response = await apiClient.submitComplaint(requestParameters);
            console.log("Complaint submitted successfully:", response);
        } catch (error) {
            console.error("Error submitting complaint:", error);
        }
    }

    function submitForm() {
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
            console.log(complaintDescription);
            console.log(selectedTags);
            console.log(locationDetails);
            console.log(selectedLocation);
            console.log(selectedSeverity);
            currentDate = formatDateTime(new Date());
            console.log(currentDate);
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

<button class="submit-button" on:click={submitForm}>Submit</button>

<!-- <ImageHandler /> -->

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
