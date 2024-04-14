<script>
    import { page } from "$app/stores";
    import { getDefaultApi } from "$lib/utils/auth";
    import { onMount } from "svelte";
    import ImageDisplyer from "$lib/components/ImageDisplayer.svelte";
    import ComplaintDetailsComponent from "$lib/components/ComplaintDetails.svelte";

    /** @typedef {import("$lib/generated").ComplaintDetails} ComplaintDetails
     * @type {ComplaintDetails | undefined} */
    let complaint;

    /** @type {ComplaintDetailsComponent}*/
    let complainComponent;

    /**
     * @typedef {Object} ImageData
     * @property {string} imageUrl - The URL of the image.
     * @property {string} fileId - The ID of the file.
     */

    /** @type {ImageData[]} */
    let fileList = [];

    /** @type {string[]}*/
    let attachmentIds = [];
    /** @type {string[]}*/
    let closureAttachmentIds = [];

    /**
     * @param {string} id
     **/
    async function fetchComplaint(id) {
        try {
            const api = getDefaultApi();
            complaint = await api.getComplaintInfo({ id });
            if (complaint.complaintInfo?.attachmentIds) {
                let counter = 0;
                for (const fileId of complaint.complaintInfo?.attachmentIds) {
                    try {
                        const fileBlob = await api.downloadFile({ fileId });
                        const imageUrl = URL.createObjectURL(fileBlob);
                        let fileName = "UserFile_" + counter++;
                        attachmentIds = [...attachmentIds, fileName];
                        fileList = [
                            ...fileList,
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
                                fileName,
                            ];
                            fileList = [
                                ...fileList,
                                { imageUrl: imageUrl, fileId: fileName },
                            ];
                        } catch (error) {
                            console.error("Error downloading file:", error);
                        }
                    }
                }
            } else {
                console.warn("File IDs are undefined in the complaint object.");
            }
        } catch (error) {
            console.error("Error fetching complain details:", error);
        }
    }

    onMount(() => {
        const complainId = $page.params.complaintId;
        fetchComplaint(complainId, false);
    });
</script>

<h2 class="main-title">Complaint Details</h2>

<ComplaintDetailsComponent
    bind:complaint
    bind:this={complainComponent}
    bind:attachmentIds
    bind:closureAttachmentIds
/>
<ImageDisplyer bind:fileList />

<style>
</style>
