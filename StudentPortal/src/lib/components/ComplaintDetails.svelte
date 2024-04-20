<script>
    import {
        ComplaintInfoLevelEnum,
        ComplaintInfoStatusEnum,
    } from "$lib/generated";
    import { getDefaultApi } from "$lib/utils/auth";
    import { getFormatDateTime } from "$lib/utils/date";
    import Dialog from "$lib/components/Dialog.svelte";
    import Snakbar from "$lib/components/Snakbar.svelte";

    /** @type {boolean}*/
    let isEdited = false;
    let partiallyClose = false;
    let escalateButton = false;
    let alertMessage = "";
    let alertTitle = "";
    let showNegativeButton = true;
    let positiveButtonLabel = "Ok";
    let negativeButtonLabel = "Cancel";
    let showAlert = false;
    let snakMessage = "";
    let isLoading = false;
    let errorColor = "#DC143C";
    let warningColor = "#ff9800";
    let successColor = "#008B8B";
    let color = warningColor;

    /** @type {Snakbar} */
    let warningSnackBar;

    /** @typedef {import("$lib/generated/models").ComplaintDetails} ComplaintDetails */
    /** @type {ComplaintDetails | undefined} */
    export let complaint;

    /** @type {import("$lib/generated").ComplaintInfo | undefined} */
    let updatedComplaint;

    $: updatedComplaint = complaint?.complaintInfo;
    $: partiallyClose =
        complaint?.complaintInfo?.status ==
        ComplaintInfoStatusEnum.PartiallyClose;
    $: escalateButton =
        complaint?.complaintInfo?.level != ComplaintInfoLevelEnum.L3 &&
        !partiallyClose &&
        complaint?.complaintInfo?.status != ComplaintInfoStatusEnum.Close;

    function updateDetails() {
        isLoading = true;
        const delay = new Promise((resolve) => {
            setTimeout(resolve, 3000);
        });

        delay.then(() => {
            if (updatedComplaint && complaint?.complaintId) {
                updatedComplaint.resolutionDate = getFormatDateTime(new Date());
                updatedComplaint.status = statusEnum;
                updatedComplaint.remarkByUser = commentValue;
                getDefaultApi().updateComplaintInfo({
                    id: complaint.complaintId,
                    complaintInfo: updatedComplaint,
                });
                clearAll();
                isLoading = false;

                alertMessage = "Update Details Successfully";
                alertTitle = "Success";
                showNegativeButton = false;
                showAlert = true;
            } else {
                isLoading = false;
                snakMessage = "Invalid data found";
                warningSnackBar.showSnackbar();
            }
        });
    }

    function handlePositive() {
        if (showNegativeButton) {
            updateDetails();
        } else {
            showNegativeButton = true;
        }
        showAlert = false;
    }

    function handleNegative() {
        showAlert = false;
    }

    function clearAll() {
        isEdited = false;
        updatedComplaint = complaint?.complaintInfo;
        commentValue = complaint?.complaintInfo?.remarkByUser;
        selectedStatus = "none";
    }

    let selectedStatus = "none";
    /** @type {string | undefined}*/
    let commentValue = "";

    /** @type {ComplaintInfoStatusEnum} */
    let statusEnum;

    function handleSelect() {
        if (selectedStatus === "none") {
            isEdited = false;
        } else {
            commentValue = complaint?.complaintInfo?.remarkByUser;
            isEdited = true;
            alertTitle = "Confirm";
            if (selectedStatus === "close") {
                alertMessage = "Are you sure, You want to close";
                statusEnum = ComplaintInfoStatusEnum.Close;
            } else {
                alertMessage = "Are you sure, You want to Reopen the Complaint";
                statusEnum = ComplaintInfoStatusEnum.ReOpen;
            }
        }
    }

    /** @param {boolean} value */
    export function openDialog(value) {
        if (value) {
            showAlert = true;
        } else {
            clearAll();
        }
    }

    function escalateComplaint() {
        let currentLevel = complaint?.complaintInfo?.level;
        if (!currentLevel) return;

        let registrationDate = complaint?.complaintInfo?.registrationDate;
        if (!registrationDate) {
            console.log("Registration date is not available.");
            snakMessage = "Registration date is not available.";
            warningSnackBar.showSnackbar();
            return;
        }

        /**@type {number | any | bigint} */
        let hoursDifference =
            new Date().getHours() - new Date(registrationDate).getHours();

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

                        snakMessage = "Successfully updated";
                        color = successColor;
                        warningSnackBar.showSnackbar();
                        console.log("Successfully updated");
                    } else {
                        snakMessage = "Network Error";
                        color = errorColor;
                        warningSnackBar.showSnackbar();
                        // alert("Network Error");
                    }
                } else {
                    snakMessage =
                        "Less than 24 hours have elapsed since registration. You can't escalate to Level 2";
                    color = warningColor;
                    warningSnackBar.showSnackbar();
                    // alert(
                    //     "Less than 24 hours have elapsed since registration. You can't escalate to Level 2",
                    // );
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
                        snakMessage = "Successfully updated";
                        color = successColor;
                        warningSnackBar.showSnackbar();
                        console.log("Successfully updated");
                    } else {
                        snakMessage = "Network Error";
                        color = errorColor;
                        warningSnackBar.showSnackbar();
                        // alert("Network Error");
                    }
                } else {
                    snakMessage =
                        "Less than 48 hours have elapsed since registration. You can't escalate to Level 3";
                    color = warningColor;
                    warningSnackBar.showSnackbar();
                    // alert(
                    //     "Less than 48 hours have elapsed since registration. You can't escalate to Level 3",
                    // );
                }
                break;
            case ComplaintInfoLevelEnum.L3:
                snakMessage = "You Can't escalate further";
                color = errorColor;
                warningSnackBar.showSnackbar();
                // alert("You Can't escalate further");
                break;
            default:
                snakMessage = "Invalid complaint level.";
                color = errorColor;
                warningSnackBar.showSnackbar();
            // alert("Invalid complaint level.");
        }
    }
</script>

{#if isLoading}
    <div class="overlay">
        <div class="loading-container">
            <div class="loading-dialog">
                <div class="loading-spinner">
                    <!-- <CircularProgress
                            indeterminate
                            style="height: 32px; width: 32px;"
                        /> -->
                </div>
            </div>
        </div>
    </div>
{/if}

<Dialog
    bind:show={showAlert}
    bind:negativeButtonLabel
    bind:positiveButtonLabel
    bind:message={alertMessage}
    bind:title={alertTitle}
    bind:showNegativeButton
    on:negative={handleNegative}
    on:positive={handlePositive}
/>

<Snakbar
    bind:this={warningSnackBar}
    bind:backgroundColor={color}
    message={snakMessage}
    duration={4000}
/>

{#if escalateButton}
    <button class="button esclate" on:click={() => escalateComplaint()}
        >Escalate to next Level</button
    >
{/if}

{#if isEdited}
    <div class="top-panel">
        <div class="panel-title" style="text-align: center;">
            Update Details
        </div>
        <div class="update-panel">
            <button class="button cancel" on:click={() => openDialog(false)}
                >Cancel</button
            >
            <button class="button update" on:click={() => openDialog(true)}
                >Update</button
            >
        </div>
    </div>
{/if}

{#if partiallyClose}
    <div class="custom-select">
        <select
            id="statusSelect"
            bind:value={selectedStatus}
            on:change={handleSelect}
        >
            <option value="none"></option>
            <option value="close">Close</option>
            <option value="reopen">ReOpen</option>
        </select>
        <label for="statusSelect">Change Status</label>
    </div>
    {#if isEdited}
        <div class="comment-container">
            <label for="admin-comment">Comment</label>
            <textarea
                placeholder="Enter your text here"
                bind:value={commentValue}
            ></textarea>
        </div>
    {/if}
{/if}

<style>
    .comment-container {
        display: flex;
        flex-direction: column;
    }
    .overlay {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        z-index: 10000;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .loading-container {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .loading-dialog {
        background-color: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.2);
    }

    .loading-spinner {
        border: 4px solid rgba(0, 0, 0, 0.1);
        border-left-color: #007bff;
        border-radius: 50%;
        width: 32px;
        height: 32px;
        animation: spin 1s linear infinite;
    }

    @keyframes spin {
        to {
            transform: rotate(360deg);
        }
    }

    .top-panel {
        position: sticky;
        background-color: rgb(245, 182, 182);
        color: red;
        display: flex;
        justify-content: space-between;
        padding: 10px;
        margin-bottom: 20px;
    }
    .panel-title {
        font-size: 18px;
        font-weight: bold;
        margin-right: 20px;
        align-self: center;
    }
    .update-panel {
        display: flex;
        gap: 10px;
    }
    .button {
        border: none;
        border-radius: 5px;
        padding: 10px 20px;
        cursor: pointer;
        transition: all 0.3s ease;
        font-size: 16px;
    }
    .button.cancel {
        background-color: red;
        color: white;
    }
    .button.update {
        background-color: #4caf50;
        color: white;
    }
    .button.esclate {
        margin-top: 1rem;
        background-color: rgb(233, 129, 129);
        color: white;
    }

    .button:hover {
        transform: scale(1.05);
    }

    textarea {
        width: 300px;
        min-height: 100px;
        padding: 10px;
        margin-bottom: 20px;
        border: 1px solid #ccc;
        border-radius: 5px;
        resize: vertical;
    }

    .custom-select select:focus {
        outline: none;
        border-color: #007bff;
    }

    .custom-select label {
        position: absolute;
        top: 50%;
        left: 10px;
        transform: translateY(-50%);
        color: #777;
        font-size: 16px;
        pointer-events: none;
        transition: 0.2s ease-out;
        padding: 10px;
    }

    .custom-select select:focus ~ label,
    .custom-select select:not(:placeholder-shown) ~ label {
        top: 5px;
        font-size: 12px;
        color: #007bff;
    }

    .custom-select {
        display: flex;
        align-items: center;
        margin-top: 1rem;
        position: relative;
        width: 100%;
    }

    .custom-select select {
        flex: 1;
        padding: 20px;
        border: 1px solid #ccc;
        border-radius: 5px;
        background-color: #fff;
        font-size: 16px;
        color: #333;
        cursor: pointer;
        margin-right: 10px;
    }

    label {
        font-weight: bold;
        margin: 0.5rem 10px 0.5rem 0;
    }
</style>
