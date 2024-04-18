<script>
    import SearchBar from "$lib/components/SearchBar.svelte";
    import { onMount } from "svelte";
    import { getDefaultApi } from "$lib/utils/auth";
    /**
     * @typedef {import("$lib/generated").ComplaintDetails } ComplaintDetails
     * @type {Array<ComplaintDetails>}
     **/
    let complaintsList = [];

    /** @type {number} */
    let page = 1;
    /** @type {number} */
    let totalPages = 0;
    /** @type {number} */
    let pageSize = 5;
    /** @type {string} */
    let userId = "12140690";

    async function fetchComplaints() {
        try {
            const complaintsPage = await getDefaultApi().getComplaintsOverview({
                pageNumber: page,
                pageSize: pageSize,
                userId: userId,
            });

            complaintsList = complaintsPage.complaintList ?? [];
            totalPages = complaintsPage.totalPages ?? 0;
        } catch (error) {
            console.error("Error fetching complain details:", error);
        }
    }

    /**
     *  @param {string | undefined} inputDate
     */
    function formatDateTime(inputDate) {
        let formattedDate = "Invalid Date";
        if (inputDate) {
            try {
                const [datePart, timePart] = inputDate.split(" ");
                const [month, day, _] = datePart.split("-");
                const [hour, minute] = timePart.split(":");
                const months = [
                    "January",
                    "February",
                    "March",
                    "April",
                    "May",
                    "June",
                    "July",
                    "August",
                    "September",
                    "October",
                    "November",
                    "December",
                ];
                const monthName = months[parseInt(month, 10) - 1];
                const time = `${hour}:${minute}`;
                formattedDate = `${day} ${monthName} ${time}`;
            } catch (error) {
                formattedDate = "Not Valid format";
            }
        }
        return formattedDate;
    }

    onMount(() => {
        fetchComplaints();
    });

    async function nextPage() {
        if (page < totalPages) {
            page++;
            await fetchComplaints();
        }
    }

    async function previousPage() {
        if (page > 1) {
            page--;
            await fetchComplaints();
        }
    }
</script>

<div>
    <SearchBar />
</div>
<table>
    <thead>
        <tr>
            <th>Serial Number</th>
            <th>Complaint Registration Number</th>
            <th>Date Of Registration</th>
            <th>Category Issue</th>
            <th>Brief Description of Issue</th>
            <th>Building Name</th>
            <th>Location Details</th>
            <th>Attach Photograph (if any)</th>
            <th>Priority</th>
            <th>Status</th>
            <th>Closeure Date</th>
            <th>Remark by Maintainence team (if any)</th>
            <th>Closeure Photograph (if any)</th>
            <th>Consent for Closure</th>
            <th>Reopen</th>
            <th>Level</th>
            <th>Remark by User</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        {#each complaintsList as complaint, index}
            <tr>
                {#if complaint}
                    <td>{index + 1}</td>
                    <td>{complaint.complaintId}</td>
                    <td
                        >{formatDateTime(
                            complaint.complaintInfo?.registrationDate,
                        )}</td
                    >
                    <td>{complaint.complaintInfo?.complaintCriteria}</td>
                    <td>{complaint.complaintInfo?.description}</td>
                    <td
                        >{complaint.complaintInfo?.buildingName}/{complaint
                            .complaintInfo?.zone}</td
                    >
                    <td>{complaint.complaintInfo?.locationDetails}</td>
                    <td> - </td>
                    <td>{complaint.complaintInfo?.priority}</td>
                    <td>{complaint.complaintInfo?.status}</td>
                    <td
                        >{formatDateTime(
                            complaint.complaintInfo?.resolutionDate,
                        )}</td
                    >
                    <td>{complaint.complaintInfo?.remarkByMaintainer}</td>
                    <td> - </td>
                    <td>Closed</td>
                    <td>Reopened</td>
                    <td>{complaint.complaintInfo?.level}</td>
                    <td>{complaint.complaintInfo?.remarkByUser}</td>
                    <td
                        ><button
                            ><a href={`/complaint/view/${complaint.complaintId}`}
                                >View</a
                            ></button
                        ></td
                    >
                {:else}
                    <td colspan="8">No complaint data available</td>
                {/if}
            </tr>
        {/each}
    </tbody>
</table>

<div class="buttons">
    <button on:click={previousPage} class="btn btn-secondary">Previous</button>
    <button on:click={nextPage} class="btn btn-secondary">Next</button>
</div>

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

    .btn {
        padding: 8px 20px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    .btn-secondary {
        background-color: #6c757d;
        color: #fff;
    }

    .btn-secondary:hover {
        background-color: #5a6268;
    }

    .buttons {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 20px;
    }
</style>
