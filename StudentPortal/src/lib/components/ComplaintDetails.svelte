<script>
    import {
        ComplaintInfoLevelEnum,
        ComplaintInfoStatusEnum,
    } from "$lib/generated";
    import { getDefaultApi } from "$lib/utils/auth";
    import Button from "@smui/button";
    import AlertDialog from "./AlertDialog.svelte";

    /** @type {boolean}*/
    let showMessage = false;
    let isEdited = false;
    let remarkEdit = false;
    let closeConsent = false;
    let isReopened = false;
    let reopenButton = false;
    let closeButton = false;
    let escalateButton = false;

    /** @type {string} */
    let selectedPriority = "none";

    /** @type {string[]}*/
    export let attachmentIds;

    /** @type {string[]}*/
    export let closureAttachmentIds;

    /** @type {string | undefined}}*/
    let closeDate;

    /** @type {string | undefined}}*/
    let remark;

    /** @typedef {import("$lib/generated/models").ComplaintDetails} ComplaintDetails
     * @type {ComplaintDetails | undefined} */
    export let complaint;

    /**
     * @typedef {Object} ImageData
     * @property {string} imageUrl
     * @property {string} fileId
     */

    /** @type {ImageData[]} */
    export let fileList = [];

    /** @type {import("$lib/generated").ComplaintInfo | undefined} */
    let updatedComplaint;
    $: updatedComplaint = complaint?.complaintInfo;
    function getFileUrl(fileName) {
        const file = fileList.find((file) => file.fileId === fileName);
        return file ? file.imageUrl : "";
    }

    /** @param {boolean} isUpdate */
    async function updateDetails(isUpdate) {
        if (isUpdate) {
            if (updatedComplaint && complaint?.complaintId) {
                updatedComplaint.resolutionDate = closeDate;
                updatedComplaint.status =
                    ComplaintInfoStatusEnum.PartiallyClose;
                getDefaultApi().updateComplaintInfo({
                    id: complaint.complaintId,
                    complaintInfo: updatedComplaint,
                });
                clearAll();
            }
        } else {
            clearAll();
        }
    }

    function clearAll() {
        isEdited = false;
        remarkEdit = false;
        remark = undefined;
        closeDate = undefined;
        selectedPriority = "none";
        updatedComplaint = complaint?.complaintInfo;
    }

    $: closeConsent =
        complaint?.complaintInfo?.status == ComplaintInfoStatusEnum.Close;
    $: isReopened =
        complaint?.complaintInfo?.status == ComplaintInfoStatusEnum.ReOpen;
    $: reopenButton =
        complaint?.complaintInfo?.status ==
        ComplaintInfoStatusEnum.PartiallyClose;
    $: closeButton =
        complaint?.complaintInfo?.status ==
        ComplaintInfoStatusEnum.PartiallyClose;
    $: escalateButton =
        complaint?.complaintInfo?.level != ComplaintInfoLevelEnum.L3 &&
        (closeConsent || reopenButton);

    function escalateComplaint() {
        let currentLevel = complaint?.complaintInfo?.level;
        if (!currentLevel) return;

        let registrationDate = complaint?.complaintInfo?.registrationDate;
        if (!registrationDate) {
            console.log("Registration date is not available.");
            return;
        }

        let regDate = new Date(registrationDate);
        let currentDate = new Date();
        let timeDifference = currentDate - regDate;
        let hoursDifference = timeDifference / (1000 * 60 * 60);

        switch (currentLevel) {
            case ComplaintInfoLevelEnum.L1:
                if (hoursDifference >= 24) {
                    if (
                        complaint &&
                        complaint.complaintInfo &&
                        complaint.complaintId
                    ) {
                        complaint.complaintInfo.level =
                            ComplaintInfoLevelEnum.L2;
                        getDefaultApi().updateComplaintInfo({
                            id: complaint.complaintId,
                            complaintInfo: complaint.complaintInfo,
                        });
                        console.log("Successfully updated");
                    } else {
                        alert("Network Error");
                    }
                } else {
                    alert(
                        "Less than 24 hours have elapsed since registration. You can't escalate to Level 2",
                    );
                }
                break;
            case ComplaintInfoLevelEnum.L2:
                if (hoursDifference >= 48) {
                    if (
                        complaint &&
                        complaint.complaintInfo &&
                        complaint.complaintId
                    ) {
                        complaint.complaintInfo.level =
                            ComplaintInfoLevelEnum.L3;
                        getDefaultApi().updateComplaintInfo({
                            id: complaint.complaintId,
                            complaintInfo: complaint.complaintInfo,
                        });
                        console.log("Successfully updated");
                    } else {
                        alert("Network Error");
                    }
                } else {
                    alert(
                        "Less than 48 hours have elapsed since registration. You can't escalate to Level 3",
                    );
                }
                break;
            case ComplaintInfoLevelEnum.L3:
                alert("You Can't escalate further");
                break;
            default:
                alert("Invalid complaint level.");
        }
    }

    let alertMessage = "";
    /** @type {ComplaintInfoStatusEnum}*/
    let status = ComplaintInfoStatusEnum.Close;
    function closeComplaint() {
        alertMessage = "Are you sure, You want to close";
        status = ComplaintInfoStatusEnum.Close;
        showMessage = true;
    }

    function updateStatus() {
        if (complaint?.complaintInfo && complaint.complaintId) {
            complaint.complaintInfo.status = status;
            getDefaultApi().updateComplaintInfo({
                id: complaint.complaintId,
                complaintInfo: complaint.complaintInfo,
            });
            console.log("updated successfully");
        } else {
            console.log("Not updated");
        }
    }

    function reopenComplaint() {
        alertMessage = "Are you sure, You want to Reopen the Complaint";
        status = ComplaintInfoStatusEnum.ReOpen;
        showMessage = true;
    }
</script>

<AlertDialog
    message={alertMessage}
    bind:show={showMessage}
    signal={updateStatus}
/>
<table>
    <tbody>
        {#if isEdited}
            <tr class="top-row">
                <td colspan="2" style="text-align: center;">Update Details</td>
                <td>
                    <div class="updatePanel">
                        <button on:click={() => updateDetails(false)}
                            >Cancel</button
                        >
                        <button on:click={() => updateDetails(true)}
                            >Update</button
                        >
                    </div>
                </td>
            </tr>
        {/if}
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
            <td>{complaint?.complaintInfo?.registrationDate}</td>
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
            <td>Attach Photograph if any</td>
            <td>
                <ul>
                    {#each attachmentIds as attachmentId}
                        <li>
                            <a href={getFileUrl(attachmentId)} target="_blank"
                                >{attachmentId}</a
                            >
                        </li>
                    {/each}
                </ul>
            </td>
        </tr>
        <tr>
            <td>Priority</td>
            <td>{complaint?.complaintInfo?.priority}</td>
        </tr>
        <tr>
            <td>Status</td>
            <td>{complaint?.complaintInfo?.status}</td>
        </tr>
        <tr>
            <td>Closure Date</td>
            {#if closeDate != undefined}
                <td>{closeDate}</td>
            {:else}
                <td>{complaint?.complaintInfo?.resolutionDate}</td>
            {/if}
        </tr>
        <tr>
            <td>Remarks by maintenance team if any</td>
            {#if remarkEdit}
                <td>
                    <input
                        type="text"
                        bind:value={remark}
                        class="form-control"
                    />
                </td>
            {:else if remark}
                <td>{remark}</td>
            {:else}
                <td>{complaint?.complaintInfo?.remarkByMaintainer}</td>
            {/if}
        </tr>
        <tr>
            <td>Closure Photograph if any</td>
            <td>
                <ul>
                    {#each closureAttachmentIds as closureAttachmentId}
                        <li>
                            <a
                                href={getFileUrl(closureAttachmentId)}
                                target="_blank">{closureAttachmentId}</a
                            >
                        </li>
                    {/each}
                </ul>
            </td>
        </tr>
        <tr>
            <td>Consent for closure (if closed)</td>
            {#if closeConsent}
                <td>Closed from Your Side</td>
            {:else}
                <td>Not Closed from Your Side</td>
            {/if}
        </tr>
        <tr>
            <td>Reopen (if closed but not satisfied)</td>
            {#if isReopened}
                <td>Reopened By You</td>
            {:else}
                <td>Not Reopened By You</td>
            {/if}
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

    {#if reopenButton}
        <Button on:click={() => reopenComplaint()}>Reopen</Button>
    {/if}
    {#if closeButton}
        <Button on:click={() => closeComplaint()}>Close</Button>
    {/if}
    {#if escalateButton}
        <Button on:click={() => escalateComplaint()}
            >Escalate to next Level</Button
        >
    {/if}
</table>

<style>
    .top-row {
        background-color: rgb(245, 182, 182);
        color: red;
    }

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
