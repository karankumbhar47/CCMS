<script>
    import SearchBar from "$lib/components/SearchBar.svelte";
    import { onMount } from "svelte";
    import { defaultApi } from "$lib/store";
    import { get } from "svelte/store";
    // import Cookies from "js-cookie";

    // const token = Cookies.get("authToken");

    /**
     * @typedef {import("$lib/generated").ComplainOverview} ComplainOverview
     */

    /** @type {Array<ComplainOverview>} */
    let complaintsList = [];

    const api = get(defaultApi);

    async function fetchComplaints() {
        try {
            const fetchedComplaints = await api.getComplaintsOverview();
            complaintsList = fetchedComplaints;
        } catch (error) {
            console.error("Error fetching complain details:", error);
        }
    }

    /**
     *  @param {string | undefined} dateTimeString
     */
    function formatDateTime(dateTimeString) {
        if (!dateTimeString) {
            return "Not specified";
        }
        const options /** @type {DateTimeFormatOptions} */ = {
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
        return date.toLocaleString(undefined, options);
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
        </tr>
    </thead>
    <tbody>
        {#each complaintsList as complaint, index}
            <tr>
                {#if complaint}
                    <td>{index + 1}</td>
                    <td>{complaint.complain}</td>
                    <td>{complaint.tags?.join(", ")}</td>
                    <td>{complaint.severity}</td>
                    <td>{complaint.location}</td>
                    <td>{complaint.complainerName}</td>
                    <td>{complaint.status}</td>
                    <td>{formatDateTime(complaint.dateTime)}</td>
                {:else}
                    <td colspan="8">No complaint data available</td>
                {/if}
            </tr>
        {/each}
    </tbody>
</table>

<style>
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
