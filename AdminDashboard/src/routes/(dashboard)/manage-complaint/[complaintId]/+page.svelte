<script>
    import { page } from "$app/stores";
    import { getDefaultApi } from "$lib/utils/auth";
    import { onMount } from "svelte";
    import ImageHandler from "$lib/components/ImageHandler.svelte";
    import ComplaintDetailsComponent from "$lib/components/ComplaintDetails.svelte";
    import AdminHandler from "$lib/components/AdminHandler.svelte";
    import CircularProgress from "@smui/circular-progress";

    let title = "User Attachment";

    /** @type {boolean}*/
    let isEdited = false;

    /** @typedef {import("$lib/generated").ComplaintDetails} ComplaintDetails
     * @type {ComplaintDetails | undefined} */
    let complaint;

    /** @type {AdminHandler}*/
    let adminHandlerComponent;

    /**
     * @typedef {Object} ImageData
     * @property {string} imageUrl - The URL of the image.
     * @property {string} fileId - The ID of the file.
     */

    /** @type {ImageData[]} */
    let attachmentIds = [];

    /** @type {ImageData[]} */
    let closureAttachmentIds = [];

    /** @param {string} id **/
    async function fetchComplaint(id) {
        try {
            isLoading = true;
            const api = getDefaultApi();
            complaint = await api.getComplaintInfo({ id });
            if (complaint.complaintInfo?.attachmentIds) {
                let counter = 0;
                for (const fileId of complaint.complaintInfo?.attachmentIds) {
                    try {
                        const fileBlob = await api.downloadFile({ fileId });
                        const imageUrl = URL.createObjectURL(fileBlob);
                        let fileName = "UserFile_" + counter++;
                        attachmentIds = [
                            ...attachmentIds,
                            { imageUrl: imageUrl, fileId: fileName },
                        ];
                    } catch (error) {
                        console.error("Error downloading file:", error);
                    }
                }
                if (
                    complaint.complaintInfo?.closureAttachmentIds != undefined
                ) {
                    for (const fileId of complaint.complaintInfo
                        ?.closureAttachmentIds) {
                        try {
                            const fileBlob = await api.downloadFile({ fileId });
                            const imageUrl = URL.createObjectURL(fileBlob);
                            let fileName = "MaintainerFile_" + counter++;
                            closureAttachmentIds = [
                                ...closureAttachmentIds,
                                { imageUrl: imageUrl, fileId: fileName },
                            ];
                        } catch (error) {
                            console.error("Error downloading file:", error);
                        }
                    }
                }
                isLoading = false;
            } else {
                console.warn("File IDs are undefined in the complaint object.");
                isLoading = false;
            }
        } catch (error) {
            console.error("Error fetching complain details:", error);
        }
    }

    let isLoading = false;
    onMount(() => {
        const complaintId = $page.params.complaintId;
        fetchComplaint(complaintId);
    });

    let sendSignal = false;
    $: sendSignal == true ? callParent() : () => {};

    function callParent() {
        console.log("call Parent");
        const complaintId = $page.params.complaintId;
        fetchComplaint(complaintId);
        sendSignal = false;
    }
</script>

{#if isLoading}
    <div class="overlay">
        <div class="loading-container">
            <div class="loading-dialog">
                <div style="display: flex; justify-content: center">
                    <CircularProgress
                        style="height: 32px; width: 32px;"
                        indeterminate
                    />
                </div>
            </div>
        </div>
    </div>
{/if}
<h2 class="main-title">Complaint Details</h2>
<ComplaintDetailsComponent bind:complaint />
{#if isEdited}
    <div class="top-panel">
        <div class="panel-title" style="text-align: center;">
            Update Details
        </div>
        <div class="update-panel">
            <button
                class="button cancel"
                on:click={() => adminHandlerComponent.openDialog(false)}
                >Cancel</button
            >
            <button
                class="button update"
                on:click={() => adminHandlerComponent.openDialog(true)}
                >Update</button
            >
        </div>
    </div>
{/if}
<AdminHandler
    bind:sendSignal
    bind:closureAttachmentIds
    bind:isEdited
    bind:complaint
    bind:this={adminHandlerComponent}
/>
<ImageHandler bind:title bind:fileList={attachmentIds} />

<style>
    .overlay {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        z-index: 1000;
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
    .button:hover {
        transform: scale(1.05);
    }
</style>
