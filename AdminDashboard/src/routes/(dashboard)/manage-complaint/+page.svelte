<script>
    import SearchBar from "$lib/components/SearchBar.svelte";
    import { onMount } from "svelte";
    import { getDefaultApi } from "$lib/utils/auth";

    /**
     * @typedef {import("$lib/generated").ComplainOverview} ComplainOverview
     * @type {Array<ComplainOverview>}
     **/
    let complaintsList = [];

    async function fetchComplaints() {
        try {
            complaintsList = await getDefaultApi().getComplaintsOverview();
            // fetchAllUsers();
        } catch (error) {
            console.error("Error fetching complain details:", error);
        }
    }

    // async function fetchAllUsers() {
    //     try {
    //         for (let index = 0; index < complaintsList.length; index++) {
    //             const complaint = complaintsList[index];
    //             if (complaint.complainerId != undefined) {
    //                 const userInfo = await getDefaultApi().getUserInfo({
    //                     userId: complaint.complainerId,
    //                 });
    //                 userDetailsList = [...userDetailsList, userInfo];
    //             }
    //         }
    //     } catch (error) {
    //         console.log("Error while fetching user details ",error)
    //     }
    // }

    /**
     *  @param {string | undefined} inputDate
     */
    function formatDateTime(inputDate) {
        let formattedDate = "Invalid Date";
        if (inputDate) {
            try {
                const [datePart, timePart] = inputDate.split(" ");
                const [month, day, year] = datePart.split("-");
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
                    <td>{formatDateTime(complaint.registrationDate)}</td>
                    <td>{complaint.userInfo?.userId || "Not Found"}</td>
                    <td>{complaint.userInfo?.name || "Not Found"}</td>
                    <td>{complaint.userInfo?.department || "Not Found"}</td>
                    <td>{complaint.userInfo?.phoneNumber || "Not Found"}</td>
                    <td>{complaint.complaintCriteria}</td>
                    <td>{complaint.description}</td>
                    <td>{complaint.buildingName}/{complaint.zone}</td>
                    <td>{complaint.locationDetails}</td>
                    <td>  -  </td>
                    <td>{complaint.priority}</td>
                    <td>{complaint.status}</td>
                    <td>{formatDateTime(complaint.resolutionDate)}</td>
                    <td>{complaint.remarkByMaintainer}</td>
                    <td>  -  </td>
                    <td>Closed</td>
                    <td>Reopened</td>
                    <td>{complaint.level}</td>
                    <td>{complaint.remarkByUser}</td>
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
