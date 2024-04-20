<script>
    import {
        ComplaintInfoStatusEnum,
        UploadFileUserTypeEnum,
    } from "$lib/generated";
    import { getDefaultApi } from "$lib/utils/auth";
    import { onMount } from "svelte";
    import ImageHandler from "./ImageHandler.svelte";
    import Select, { Option } from "@smui/select";
    import { getFormatDateTime } from "$lib/utils/dateTime";
    import Dialog, { Title, Content, Actions } from "@smui/dialog";
    import CircularProgress from "@smui/circular-progress";
    import Button, { Label } from "@smui/button";

    export let sendSignal = false;
    /** @type {string | undefined} */
    let selectedStatus = undefined;

    let title = "Maintainter Attachment";
    let title1 = "Selected Files";

    /**
     * @typedef {Object} ImageData
     * @property {string} imageUrl
     * @property {string} fileId
     */
    /** @typedef {import("$lib/generated/models").ComplaintDetails} ComplaintDetails
     * @type {ComplaintDetails | undefined} */
    export let complaint;
    /** @type {boolean} */
    export let isEdited = true;

    /** @type {Array<ImageData>}*/
    export let closureAttachmentIds;

    /** @type {Array<ImageData>}*/
    let selectedAttachments = [];

    let statusClose = false;
    /** @type {string | undefined}*/
    let commentValue = undefined;
    let closeButton = false;
    let inProgressButton = false;
    let showCommentInput = false;
    let isLoading = false;
    /** @type {import("$lib/generated").ComplaintInfo | undefined} */
    let updatedComplaint;

    $: updatedComplaint = complaint?.complaintInfo;
    $: closeButton =
        complaint?.complaintInfo?.status == ComplaintInfoStatusEnum.Open ||
        complaint?.complaintInfo?.status == ComplaintInfoStatusEnum.ReOpen ||
        complaint?.complaintInfo?.status == ComplaintInfoStatusEnum.InProgress;
    $: inProgressButton =
        complaint?.complaintInfo?.status == ComplaintInfoStatusEnum.ReOpen ||
        complaint?.complaintInfo?.status == ComplaintInfoStatusEnum.Open;
    $: showCommentInput = closeButton || inProgressButton;

    let MAX_IMAGES = 5 - closureAttachmentIds.length;
    const MAX_FILE_SIZE_MB = 2;

    /** @type {Array.<{selectedFile: null | File; fileId: string | null; imageUrl: string | null}>} boxes*/
    let boxes = Array.from({ length: MAX_IMAGES }, () => ({
        selectedFile: null,
        fileId: null,
        imageUrl: null,
    }));

    /** @param {{ target: { files: Array<File>; }; }} event */
    function handleFileSelect(event) {
        console.log(closureAttachmentIds.length);
        isEdited = true;
        const files = event.target.files;
        console.log(MAX_IMAGES);

        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            let selectedFileNum = selectedAttachments.length + 1;
            if (!file.type.startsWith("image/")) {
                alert("Please select only images.");
                continue;
            }
            if (file.size > MAX_FILE_SIZE_MB * 1024 * 1024) {
                alert(`File size exceeds ${MAX_FILE_SIZE_MB}MB.`);
                continue;
            }
            if (selectedFileNum >= MAX_IMAGES + 1) {
                alert(`Maximum 5 images allowed.`);
                break;
            }
            const imageUrl = URL.createObjectURL(file);
            let fileName = "File_" + selectedFileNum;
            boxes = [
                ...boxes,
                { imageUrl: imageUrl, selectedFile: file, fileId: fileName },
            ];
            selectedAttachments = [
                ...selectedAttachments,
                { imageUrl: imageUrl, fileId: fileName },
            ];
        }
    }

    /** @param {boolean} isUpdate */
    async function updateDetails(isUpdate) {
        console.log("called update details");
        console.log(isUpdate);
        if (isUpdate) {
            isLoading = true;
            if (updatedComplaint && complaint?.complaintId) {
                updatedComplaint.remarkByMaintainer = commentValue;
                updatedComplaint.resolutionDate = getFormatDateTime(new Date());
                updatedComplaint.status =
                    selectedStatus == "partiallyClose"
                        ? ComplaintInfoStatusEnum.PartiallyClose
                        : ComplaintInfoStatusEnum.InProgress;
                for (let index = 0; index < boxes.length; index++) {
                    const { selectedFile } = boxes[index];
                    if (selectedFile) {
                        try {
                            const response = await getDefaultApi().uploadFile({
                                userType: UploadFileUserTypeEnum.Resolver,
                                imageFile: selectedFile,
                            });

                            if (updatedComplaint.closureAttachmentIds) {
                                updatedComplaint.closureAttachmentIds.push(
                                    response,
                                );
                            } else {
                                updatedComplaint.closureAttachmentIds = [
                                    response,
                                ];
                            }
                            console.log(
                                "File uploaded successfully. File ID:",
                                updatedComplaint?.closureAttachmentIds,
                            );
                        } catch (error) {
                            console.error("Error uploading image:", error);
                        }
                    }
                }
                getDefaultApi().updateComplaintInfo({
                    id: complaint.complaintId,
                    complaintInfo: updatedComplaint,
                });
                clearAll();
            }
            isLoading = false;
            console.log("call parent");
            sendSignal = true;
        } else {
            clearAll();
            isLoading = false;
        }
    }

    function clearAll() {
        isEdited = false;
        commentValue = undefined;
        updatedComplaint = complaint?.complaintInfo;

        statusClose = false;
        selectedStatus = "none";
        clearImages();
    }

    function clearImages() {
        boxes = Array.from({ length: MAX_IMAGES }, () => ({
            selectedFile: null,
            fileId: null,
            imageUrl: null,
        }));
        selectedAttachments = [];
    }

    function addImage() {
        let element = document.getElementById("fileInput");
        if (element) element.click();
    }

    $: MAX_IMAGES = 5 - closureAttachmentIds.length;
    onMount(() => {
        commentValue = complaint?.complaintInfo?.remarkByMaintainer;
        const handleReload = () => {
            if (!isLoading) {
                window.location.reload();
            }
        };

        window.addEventListener("beforeunload", handleReload);

        return () => {
            window.removeEventListener("beforeunload", handleReload);
        };
    });

    function selectHandle() {
        console.log(selectedStatus);
        if (
            selectedStatus === "" ||
            selectedStatus === "none" ||
            selectedStatus === undefined
        ) {
            isEdited = false;
            console.log(isEdited);
            clearImages();
        } else {
            commentValue = complaint?.complaintInfo?.remarkByMaintainer;
            if (selectedStatus === "partiallyClose") {
                statusClose = true;
            } else {
                statusClose = false;
                clearImages();
            }
            isEdited = true;
            console.log(isEdited);
        }
    }

    let open = false;

    /** @param {boolean} value */
    export function openDialog(value) {
        if (value) {
            open = true;
        } else {
            updateDetails(false);
        }
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

{#if showCommentInput}
    <div class="container">
        <label for="admin-action">Action</label>
        <div class="button-container">
            <Select
                variant="outlined"
                bind:value={selectedStatus}
                on:click={selectHandle}
                label="Change Status"
            >
                <Option value="none"></Option>
                {#if closeButton}
                    <Option value="partiallyClose">Partially Close</Option>
                {/if}
                {#if inProgressButton}
                    <Option value="inProgress">In Progress</Option>
                {/if}
            </Select>
            {#if statusClose}
                <button class="add-button" on:click={addImage}>Add Image</button
                >
            {/if}
            <input
                type="file"
                id="fileInput"
                accept="image/*"
                style="display: none;"
                multiple
                on:change={handleFileSelect}
            />
        </div>
        {#if isEdited}
            <label for="admin-comment">Comment</label>
            <textarea
                placeholder="Enter your text here"
                bind:value={commentValue}
            ></textarea>
        {/if}
    </div>
{/if}

{#if selectedAttachments.length > 0}
    <ImageHandler bind:title={title1} bind:fileList={selectedAttachments} />
{/if}

{#if closureAttachmentIds.length > 0}
    <ImageHandler bind:title bind:fileList={closureAttachmentIds} />
{/if}

<Dialog
    bind:open
    aria-labelledby="simple-title"
    aria-describedby="simple-content"
>
    <!-- Title cannot contain leading whitespace due to mdc-typography-baseline-top() -->
    <Title id="simple-title">Confirm</Title>
    <Content id="simple-content"
        >Are you sure you want to change status to {selectedStatus}</Content
    >
    <Actions>
        <Button
            on:click={() => {
                updateDetails(false);
            }}
        >
            <Label>No</Label>
        </Button>
        <Button
            on:click={() => {
                updateDetails(true);
            }}
        >
            <Label>Yes</Label>
        </Button>
    </Actions>
</Dialog>

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

    .add-button {
        margin-left: 3rem;
    }

    .button-container {
        display: flex;
        flex-direction: row;
    }

    .container {
        display: flex;
        flex-direction: column;
        justify-content: center;
    }

    label {
        font-weight: bold;
        margin: 0.5rem;
    }

    textarea {
        width: 300px;
        min-height: 100px; /* Set minimum height */
        padding: 10px;
        margin-bottom: 20px;
        border: 1px solid #ccc;
        border-radius: 5px;
        resize: vertical; /* Allow vertical resizing */
    }

    button {
        padding: 10px 20px;
        margin: 5px;
        border: none;
        border-radius: 5px;
        background-color: #007bff;
        color: white;
        cursor: pointer;
    }

    button:hover {
        background-color: #0056b3;
    }
</style>
