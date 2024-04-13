<script>
    import {
        ComplaintInfoPriorityEnum,
        ComplaintInfoStatusEnum,
        UploadFileUserTypeEnum,
    } from "$lib/generated";
    import { getDefaultApi } from "$lib/utils/auth";
    import ImageHandler from "./ImageHandler.svelte";
    import { onMount } from "svelte";

    /** @type {boolean}*/
    let isEdited = false;
    let hideThirdColumn = true;
    let isClosed = false;

    /**
     * @typedef {import("$lib/generated").ComplaintDetails} ComplaintDetails
     * @type {ComplaintDetails | undefined}
     */
    export let complaint;

    /** @type {string} */
    let selectedPriority;

    /** @type {string | undefined}}*/
    let closeDate;
    /** @type {string | undefined}}*/
    let remark;
    let remarkEdit = false;

    /**
     * @typedef {Object} ImageData
     * @property {string} imageUrl - The URL of the image.
     * @property {string} fileId - The ID of the file.
     */

    /** @type {ImageData[]} */
    export let fileList = [];

    /** @type {ImageData[]} */
    let selectedFiles = [];

    /** @type {string[]}*/
    export let attachmentIds;

    /** @type {string[]}*/
    export let closureAttachmentIds;

    let updatedComplaint = complaint?.complaintInfo;
    const MAX_IMAGES = 5;
    const MAX_FILE_SIZE_MB = 5;

    /** @param {string} fileName */
    function getFileUrl(fileName) {
        const file = fileList.find((file) => file.fileId === fileName);
        return file ? file.imageUrl : "";
    }

    function closeComplaint() {
        isEdited = true;
        isClosed = true;
        closeDate = formatDateTime(new Date());
        if (updatedComplaint) {
            updatedComplaint.resolutionDate = closeDate;
            updatedComplaint.status = ComplaintInfoStatusEnum.PartiallyClose;
            console.log("close complete");
        } else {
            console.log("not complete");
        }
    }

    function openComplaint() {
        isEdited = true;
        isClosed = false;
        closeDate = undefined;
        if (updatedComplaint) {
            updatedComplaint.resolutionDate =
                complaint?.complaintInfo?.resolutionDate;
            updatedComplaint.status = complaint?.complaintInfo?.status;
            console.log("open complete");
        } else {
            console.log("not complete");
        }
    }

    function giveRemark() {
        isEdited = true;
        remarkEdit = true;
    }

    function doneRemark() {
        isEdited = true;
        remarkEdit = false;
        let updatedRemark;
        if (remark && updatedComplaint) {
            updatedRemark = remark;
            updatedComplaint.remarkByMaintainer = updatedRemark;
        }
    }

    $: updatedComplaint = complaint?.complaintInfo;
    $: hideThirdColumn =
        complaint?.complaintInfo?.status != ComplaintInfoStatusEnum.Open;
    // $: isClosed = complaint?.complaintInfo?.status!=ComplaintInfoStatusEnum.Open ;

    onMount(() => {
        if (complaint) {
            updatedComplaint = complaint.complaintInfo;
            console.log("got");
        } else {
            console.log("not got");
        }
    });

    function changePriority() {
        if (updatedComplaint) {
            if (
                selectedPriority != "none" &&
                selectedPriority != updatedComplaint?.priority
            ) {
                if (selectedPriority == "medium") {
                    updatedComplaint.priority =
                        ComplaintInfoPriorityEnum.Medium;
                } else if (selectedPriority == "urgent") {
                    updatedComplaint.priority =
                        ComplaintInfoPriorityEnum.Urgent;
                } else {
                    updatedComplaint.priority =
                        ComplaintInfoPriorityEnum.Normal;
                }
            }
        }
    }

    function addImage() {
        document.getElementById("fileInput").click();
    }

    /** @type {Array.<{selectedFile: null | File; fileId: string | null; imageUrl: string | null}>} boxes*/
    let boxes = Array.from({ length: MAX_IMAGES }, () => ({
        selectedFile: null,
        fileId: null,
        imageUrl: null,
    }));

    /** @param {{ target: { files: any; }; }} event */
    function handleFileSelect(event) {
        isEdited = true;
        const files = event.target.files; // Get the selected files

        for (let i = 0; i < files.length; i++) {
            const file = files[i];

            if (!file.type.startsWith("image/")) {
                alert("Please select only images.");
                continue;
            }

            if (file.size > MAX_FILE_SIZE_MB * 1024 * 1024) {
                alert(`File size exceeds ${MAX_FILE_SIZE_MB}MB.`);
                continue;
            }

            if (selectedFiles.length >= MAX_IMAGES) {
                alert(`Maximum ${MAX_IMAGES} images allowed.`);
                break;
            }
            const imageUrl = URL.createObjectURL(file);
            boxes = [
                ...boxes,
                { imageUrl: imageUrl, selectedFile: file, fileId: null },
            ];
            let fileName = "File_" + selectedFiles.length;
            selectedFiles = [
                ...selectedFiles,
                { imageUrl: imageUrl, fileId: fileName },
            ];
        }
    }

    function cancelUpdate() {
        isEdited = false;
        remarkEdit = false;
        remark = undefined;
        closeDate = undefined;
        updatedComplaint = complaint?.complaintInfo;
    }

    async function updateDetails() {
        if (updatedComplaint && complaint?.complaintId) {
            changePriority();
            for (let index = 0; index < boxes.length; index++) {
                const { selectedFile } = boxes[index];
                if (selectedFile) {
                    try {
                        const response = await getDefaultApi().uploadFile({
                            userType: UploadFileUserTypeEnum.User,
                            imageFile: selectedFile,
                        });

                        if (updatedComplaint.closureAttachmentIds) {
                            updatedComplaint.closureAttachmentIds.push(
                                response,
                            );
                        } else {
                            updatedComplaint.closureAttachmentIds = [response];
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
        } else {
            console.log("complaint info is empty");
        }
    }

    /** @param {string | number | Date} date */
    function formatDateTime(date) {
        const options = {
            year: "numeric",
            month: "2-digit",
            day: "2-digit",
            hour: "2-digit",
            minute: "2-digit",
            timeZoneName: "short",
        };

        const dateString = new Date(date).toLocaleString("en-US", options);
        return dateString.replace(/\//g, "-").replace(",", "");
    }
</script>

<table>
    <tbody>
        {#if isEdited}
            <tr class="top-row">
                <td colspan="2" style="text-align: center;"
                    >Update Details to Server</td
                >
                <td>
                    <div class="updatePanel">
                        <button on:click={cancelUpdate}>Cancel</button>
                        <button on:click={updateDetails}>Update</button>
                    </div>
                </td>
            </tr>
        {/if}
        <tr>
            <th colspan="2" style="text-align: center;"
                >Complain Registration Details</th
            >
            <th style={hideThirdColumn ? "display: none" : ""}>Edit</th>
        </tr>
        <tr>
            <td>Complain registration number</td>
            <td>{complaint?.complaintId}</td>
            <td style={hideThirdColumn ? "display: none" : ""}></td>
        </tr>
        <tr>
            <td>Date of Registration</td>
            <td>{complaint?.complaintInfo?.registrationDate}</td>
            <td style={hideThirdColumn ? "display: none" : ""}></td>
        </tr>
        <tr>
            <td>User ID</td>
            <td>{complaint?.userInfo?.userId}</td>
            <td style={hideThirdColumn ? "display: none" : ""}></td>
        </tr>
        <tr>
            <td>User Name</td>
            <td>{complaint?.userInfo?.name}</td>
            <td style={hideThirdColumn ? "display: none" : ""}></td>
        </tr>
        <tr>
            <td>Department</td>
            <td>{complaint?.userInfo?.department}</td>
            <td style={hideThirdColumn ? "display: none" : ""}></td>
        </tr>
        <tr>
            <td>Phone Number</td>
            <td>{complaint?.userInfo?.phoneNumber}</td>
            <td style={hideThirdColumn ? "display: none" : ""}></td>
        </tr>
        <tr>
            <td>Category of issue</td>
            <td>{complaint?.complaintInfo?.complaintCriteria}</td>
            <td style={hideThirdColumn ? "display: none" : ""}></td>
        </tr>
        <tr>
            <td>Brief Description of Issue</td>
            <td>{complaint?.complaintInfo?.description}</td>
            <td style={hideThirdColumn ? "display: none" : ""}></td>
        </tr>
        <tr>
            <td>Location (building/zone)</td>
            <td
                >{complaint?.complaintInfo?.buildingName}/{complaint
                    ?.complaintInfo?.zone}</td
            >
            <td style={hideThirdColumn ? "display: none" : ""}></td>
        </tr>
        <tr>
            <td>Location(Room No.)</td>
            <td>{complaint?.complaintInfo?.locationDetails}</td>
            <td style={hideThirdColumn ? "display: none" : ""}></td>
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
            <td style={hideThirdColumn ? "display: none" : ""}></td>
        </tr>
        <tr>
            <td>Priority</td>
            <td>{complaint?.complaintInfo?.priority}</td>
            <td style={hideThirdColumn ? "display: none" : ""}>
                <select id="priority" bind:value={selectedPriority}>
                    <option value="none"></option>
                    <option value="medium">Medium</option>
                    <option value="urgent">Urgent</option>
                    <option value="normal">Normal</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>Status</td>
            <td>{complaint?.complaintInfo?.status}</td>
            <td style={hideThirdColumn ? "display: none" : ""}>
                {#if isClosed}
                    <button on:click={openComplaint}>Not Close</button>
                {:else}
                    <button on:click={closeComplaint}>Close</button>
                {/if}
            </td>
        </tr>
        <tr>
            <td>Closure Date</td>
            {#if closeDate}
                <td>{closeDate}</td>
            {:else}
                <td>{complaint?.complaintInfo?.resolutionDate}</td>
            {/if}
            <td style={hideThirdColumn ? "display: none" : ""}></td>
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
            <td style={hideThirdColumn ? "display: none" : ""}>
                {#if remarkEdit}
                    <button on:click={doneRemark}>Done</button>
                {:else}
                    <button on:click={giveRemark}>Edit</button>
                {/if}
            </td>
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
            <td style={hideThirdColumn ? "display: none" : ""}>
                <button on:click={addImage}>Add Image</button>
                <input
                    type="file"
                    id="fileInput"
                    accept="image/*"
                    style="display: none;"
                    multiple
                    on:change={handleFileSelect}
                />
            </td>
        </tr>
        <tr>
            <td>Consent for closure (if closed)</td>
            <td>{complaint?.complaintInfo?.status}</td>
            <td style={hideThirdColumn ? "display: none" : ""}></td>
        </tr>
        <tr>
            <td>Reopen (if closed but not satisfied)</td>
            <td></td>
            <td style={hideThirdColumn ? "display: none" : ""}></td>
        </tr>
        <tr>
            <td>Escalate to next level</td>
            <td>{complaint?.complaintInfo?.level}</td>
            <td style={hideThirdColumn ? "display: none" : ""}></td>
        </tr>
        <tr>
            <td>Remarks by User</td>
            <td>{complaint?.complaintInfo?.remarkByUser}</td>
            <td style={hideThirdColumn ? "display: none" : ""}></td>
        </tr>
    </tbody>
</table>

{#if selectedFiles.length > 0}
    <ImageHandler bind:fileList={selectedFiles} />
{/if}

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
