<script>
    import { getFormattedDate } from "$lib/utils/dateTime";

    /** @typedef {import("$lib/generated/models").ComplaintDetails} ComplaintDetails
     * @type {ComplaintDetails | undefined} */
    export let complaint;
</script>

<table>
    <tbody>
        <tr>
            <th colspan="2" style="text-align: center;"
                >Complain Registration Details</th
            >
        </tr>
        <tr>
            <td>Complain registration number</td>
            <td>{complaint?.complaintId}</td>
        </tr>
        <tr>
            <td>Date of Registration</td>
            <td
                >{getFormattedDate(
                    complaint?.complaintInfo?.registrationDate,
                )}</td
            >
        </tr>
        <tr>
            <td>User ID</td>
            <td>{complaint?.userInfo?.userId}</td>
        </tr>
        <tr>
            <td>User Name</td>
            <td>{complaint?.userInfo?.name}</td>
        </tr>
        <tr>
            <td>Department</td>
            <td>{complaint?.userInfo?.department}</td>
        </tr>
        <tr>
            <td>Phone Number</td>
            <td>{complaint?.userInfo?.phoneNumber}</td>
        </tr>
        <tr>
            <td>Category of issue</td>
            <td>{complaint?.complaintInfo?.complaintCriteria}</td>
        </tr>
        <tr>
            <td>Brief Description of Issue</td>
            <td>{complaint?.complaintInfo?.description}</td>
        </tr>
        <tr>
            <td>Location (building/zone)</td>
            <td
                >{complaint?.complaintInfo?.buildingName}/{complaint
                    ?.complaintInfo?.zone}</td
            >
        </tr>
        <tr>
            <td>Location(Room No.)</td>
            <td>{complaint?.complaintInfo?.locationDetails}</td>
        </tr>
        <tr>
            <td>Status</td>
            <td>{complaint?.complaintInfo?.status}</td>
        </tr>
        <tr>
            <td>Closure Date</td>
            <td>{getFormattedDate(complaint?.complaintInfo?.resolutionDate)}</td
            >
        </tr>
        <tr>
            <td>Remarks by maintenance team if any</td>
            <td>{complaint?.complaintInfo?.remarkByMaintainer}</td>
        </tr>
        <tr>
            <td>Escalate to next level</td>
            <td>{complaint?.complaintInfo?.level}</td>
        </tr>
        <tr>
            <td>Remarks by User</td>
            <td>{complaint?.complaintInfo?.remarkByUser}</td>
        </tr>
    </tbody>
</table>


<style>
    

    table {
        width: 100%;
        border-collapse: collapse;
    }

    th,
    td {
        border: 1px solid #dddddd;
        padding: 8px;
        text-align: left;
    }

    th {
        background-color: #f2f2f2;
    }

    td:first-child {
        font-weight: bold;
    }
</style>
