<script>
    // @ts-nocheck

    import { page } from "$app/stores";
    import { DefaultApi } from "$lib/generated";
    import { onMount } from "svelte";

    const complainId = $page.params.complaintId;

    /**
     * @type {
     * {
     * serialNumber: number;
     * complain: string;
     * tags: string[];
     * severity: string;
     * location: string;
     * status: string;
     * complainer: { id: number; name: string; };
     * Date: string;
     * } |
     * {
     * serialNumber: number;
     * complain: string;
     * tags: string[];
     * severity: string;
     * location: string;
     * complainer: { id: number; name: string; };
     * status?: undefined;
     *  Date?: undefined;
     * } |
     * undefined
     * }
     */
    let complaint;

    /**
     * @param {string} complainId
     */
    async function fetchComplaint(complainId) {
        try {
            const api = new DefaultApi();
            const getComplaintRequest = { complaintId: complainId }; // Corrected parameter name
            const param = { complaintId: getComplaintRequest }; // Corrected parameter name
            // @ts-ignore
            complaint = await api.getComplaint(param);
        } catch (error) {
            console.error("Error fetching complain details:", error);
        }
    }

    onMount(() => {
        fetchComplaint(complainId);
    });
</script>

<h2 class="main-title">Complaint Details</h2>
<div class="complaint-details">
    <ul>
        <li><strong>Serial Number:</strong> {complaint.serialNumber}</li>
        <li><strong>Complain:</strong> {complaint.complain}</li>
        <li><strong>Tags:</strong> {complaint.tags.join(", ")}</li>
        <li><strong>Severity:</strong> {complaint.severity}</li>
        <li><strong>Location:</strong> {complaint.location}</li>
        <li><strong>Status:</strong> {complaint.status || "Not specified"}</li>
        <li><strong>Complainer ID:</strong> {complaint.complainer.id}</li>
        <li><strong>Complainer Name:</strong> {complaint.complainer.name}</li>
        <li><strong>Date:</strong> {complaint.Date || "Not specified"}</li>
    </ul>
</div>

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
