<script>
    import SearchBar from "$lib/components/SearchBar.svelte";
    import { onMount } from "svelte";
    import { getDefaultApi } from "$lib/utils/auth";
    import Select, { Option } from "@smui/select";

    let statusList = ["All", "Close", "Open", "InProgress"];

    let value = "All";

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

    let status = "";
    let toDate = "";
    let fromDate = "";
    let filterOn = false;
    async function filter() {
        if (!filterOn) {
            page = 1;
            filterOn = true;
        }
        try {
            status = value;
            if (value === "All") {
                status = "";
            }
            const filteredComplaintsPage =
                await getDefaultApi().filterComplaint({
                    tags: [],
                    totime: toDate,
                    fromtime: fromDate,
                    status: status,
                    pageNumber: page,
                    pageSize: pageSize,
                });
            complaintsList = filteredComplaintsPage.complaintList ?? [];
            totalPages = filteredComplaintsPage.totalPages ?? 0;
            console.log(complaintsList);
        } catch (err) {
            console.error(err);
        }
    }

    async function fetchComplaints() {
        try {
            const complaintsPage = await getDefaultApi().getComplaintsOverview({
                pageNumber: page,
                pageSize: pageSize,
                userId: undefined,
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
            if (filterOn) {
                await filter();
            } else {
                await fetchComplaints();
            }
        }
    }

    async function previousPage() {
        if (page > 1) {
            page--;
            if (filterOn) {
                await filter();
            } else {
                await fetchComplaints();
            }
        }
    }
    async function resetFilter() {
        status = "";
        toDate = "";
        fromDate = "";
        filterOn = false;
        page = 1;
        await fetchComplaints();
    }
</script>

<div>
    <label for="ToDate">To Date</label>
    <input type="date" id="ToDate" bind:value={toDate} />
    <label for="FromDate">From Date</label>
    <input type="date" id="FromDate" bind:value={fromDate} />
    <Select bind:value label="Select STATUS">
        {#each statusList as status}
            <Option value={status}>{status}</Option>
        {/each}
    </Select>

    <button type="submit" on:click={filter}>filter</button>
    <button type="submit" on:click={resetFilter}>reset</button>
</div>

<!-- <div class="search-container">
    <SearchBar />
</div> -->

<table class="table-container">
    <thead>
        <tr>
            <th>Serial Number</th>
            <th>Complaint Registration Number</th>
            <th>Date Of Registration</th>
            <th>User Id</th>
            <th>User Name</th>
            <th>Department</th>
            <th>Phone Number</th>
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
                    <td>{complaint.userInfo?.userId || "Not Found"}</td>
                    <td>{complaint.userInfo?.name || "Not Found"}</td>
                    <td>{complaint.userInfo?.department || "Not Found"}</td>
                    <td>{complaint.userInfo?.phoneNumber || "Not Found"}</td>
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
                            ><a
                                href={`/manage-complaint/${complaint.complaintId}`}
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

    .search-container {
        position: fixed;
        top: 10vh;
        left: 250px;
        width: calc(100vw - 250px);
        z-index: 99;
        background-color: #fff;
        padding: 10px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        overflow-x: hidden;
    }

    .table-container {
        margin-top: 100px;
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
        position: fixed;
        z-index: 100;
        bottom: 20px;
        left: 250px;
        width: 75%;
        display: flex;
        justify-content: space-between;
        padding: 0 20px;
        box-sizing: border-box;
    }
</style>
