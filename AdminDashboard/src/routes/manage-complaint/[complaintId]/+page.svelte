<script>
    import { page } from "$app/stores";
    import { defaultApi } from "$lib/store";
    import { get } from "svelte/store";
    import { onMount } from "svelte";
    import ImageHandler from "$lib/components/ImageHandler.svelte";

    /**
     * @typedef {import("$lib/generated").ComplainOverview} ComplainOverview
     */

    /** @type {ComplainOverview | undefined} */
    let complaint;

    /** @type {string[]} */
    let imageUrls = [];

    /**
     * @param {string} id
     */
    async function fetchComplaint(id) {
        try {
            const api = get(defaultApi);
            complaint = await api.getComplaintInfo({ id });

            if (complaint.fileIds) {
                for (const fileId of complaint.fileIds) {
                    try {
                        const fileBlob = await api.downloadFile({ fileId });
                        const imageUrl = URL.createObjectURL(fileBlob);
                        imageUrls = [...imageUrls, imageUrl];
                    } catch (error) {
                        console.error("Error downloading file:", error);
                    }
                }
            } else {
                console.warn("File IDs are undefined in the complaint object.");
            }
        } catch (error) {
            console.error("Error fetching complain details:", error);
        }
    }

    onMount(() => {
        const complainId = $page.params.complaintId;
        fetchComplaint(complainId);
    });
</script>

<h2 class="main-title">Complaint Details</h2>
<div class="complaint-details">
    <ul>
        <li>
            <strong>Complain:</strong>
            {complaint?.complain || "Not specified"}
        </li>
        <li>
            <strong>Tags:</strong>
            {complaint?.tags?.join(", ") || "Not specified"}
        </li>
        <li>
            <strong>Severity:</strong>
            {complaint?.severity || "Not specified"}
        </li>
        <li>
            <strong>Location:</strong>
            {complaint?.location || "Not specified"}
        </li>
        <li><strong>Status:</strong> {complaint?.status || "Not specified"}</li>
        <li>
            <strong>Complainer ID:</strong>
            {complaint?.complainerId || "Not specified"}
        </li>
        <li>
            <strong>Complainer Name:</strong>
            {complaint?.complainerName || "Not specified"}
        </li>
        <li><strong>Date:</strong> {complaint?.dateTime || "Not specified"}</li>
    </ul>
</div>
<ImageHandler bind:imageUrls />

<style>
    .complaint-details {
        margin: 20px;
        padding: 20px;
        border: 1px solid #ccc;
        border-radius: 5px;
        background-color: #f9f9f9;
    }

    .main-title {
        align-self: center;
    }

    h2 {
        margin-bottom: 10px;
    }

    ul {
        list-style-type: none;
        padding: 0;
    }

    li {
        margin-bottom: 5px;
    }
</style>
