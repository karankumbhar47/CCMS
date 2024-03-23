<script>
    import SearchBar from "$lib/components/SearchBar.svelte";
    import { onMount } from "svelte";
    import { writable } from "svelte/store";
    import { DefaultApi } from "$lib/generated/apis/DefaultApi";

    export const selectedComplaint = writable(null);
    export const selectedComplaintId = writable(null);
    const api = new DefaultApi();

    /**
     * @type {any[]}
     */
    let complaintsList = [];

    async function fetchComplaints() {
        try {
            const fetchedComplaints = await api.getComplaintsOverview();
            complaintsList = fetchedComplaints;
            console.log(complaintsList);
        } catch (error) {
            console.error("Error fetching complain details:", error);
        }
    }

    /**
     * @param {string | number | Date} dateTimeString
     */
    function formatDateTime(dateTimeString) {
        console.log(dateTimeString);
        const options = {
            weekday: "long",
            year: "numeric",
            month: "long",
            day: "numeric",
            hour: "numeric",
            minute: "numeric",
            hour12: true,
        };
        const date = new Date(dateTimeString);
        // @ts-ignore
        return date.toLocaleString(undefined, options); // Return the formatted date and time
    }

    onMount(() => {
        fetchComplaints();
    });
</script>

<div>
    <SearchBar />
</div>
<table>
    <thead>
        <tr>
            <th>Serial Number</th>
            <th>Complain</th>
            <th>Tags</th>
            <th>Severity</th>
            <th>Location</th>
            <th>Complainer</th>
            <th>Status</th>
            <th>Date</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        {#each complaintsList as complaint, index}
            <tr>
                <td>{index + 1}</td>
                <td>{complaint.complain}</td>
                <td>{complaint.tags.join(", ")}</td>
                <td>{complaint.severity}</td>
                <td>{complaint.location}</td>
                <td>{complaint.complainerName}</td>
                <td>{complaint.status}</td>
                <td>{formatDateTime(complaint.dateTime)}</td>
                <td
                    ><button
                        ><a href={`/manage-complaint/${complaint.complainId}`}
                            >View</a
                        ></button
                    ></td
                >
            </tr>
        {/each}
    </tbody>
</table>

<style>
    /* Add your table styling here */
    table {
        width: 100%;
        border-collapse: collapse;
    }
    th,
    td {
        padding: 8px;
        border: 1px solid #ddd;
    }
    th {
        background-color: #f2f2f2;
    }
</style>
