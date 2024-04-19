<script>
    /**
     * @typedef {Object} ImageData
     * @property {string} imageUrl - The URL of the image.
     * @property {string} fileId - The ID of the file.
     */

    /** @type {ImageData[]} */
    export let fileList = [];
    /** @type {string}*/
    export let title;

    /** @type {number} */
    let number;
    $: number = fileList.length;

    let activeOuterPanelIndex = -1;
    let activeInnerPanelIndex = -1;

    /**
     * @param {number} index
     */
    function toggleOuterPanel(index) {
        activeOuterPanelIndex = activeOuterPanelIndex === index ? -1 : index;
    }

    /**
     * @param {number} index
     * @param {MouseEvent & { currentTarget: EventTarget & HTMLDivElement; }} event
     */
    function toggleInnerPanel(index, event) {
        if (activeInnerPanelIndex === index) {
            activeInnerPanelIndex = -1; // Collapse the active inner panel
        } else {
            activeInnerPanelIndex = index; // Expand the clicked inner panel
        }
        event.stopPropagation(); // Stop event propagation to prevent outer panel toggle
    }
</script>

<div class="image-container">
    <div class="accordion">
        <div class="panel outer" class:active={activeOuterPanelIndex === 0}>
            <!-- svelte-ignore a11y-click-events-have-key-events -->
            <!-- svelte-ignore a11y-no-static-element-interactions -->
            <div class="header" on:click={() => toggleOuterPanel(0)}>
                <div class="container-title">
                    <div class="name">{title}</div>
                    <div class="number">{number}</div>
                </div>
            </div>
            <div class="content">
                <div class="accordion-container">
                    {#each fileList as file, index}
                        <div class="panel inner">
                            <!-- svelte-ignore a11y-click-events-have-key-events -->
                            <!-- svelte-ignore a11y-no-static-element-interactions -->
                            <div
                                class="header"
                                on:click={(event) =>
                                    toggleInnerPanel(index, event)}
                            >
                                {file.fileId}
                            </div>
                            <div
                                class="content"
                                class:active={activeInnerPanelIndex === index}
                            >
                                <!-- svelte-ignore a11y-img-redundant-alt -->
                                <img src={file.imageUrl} alt="Image" />
                            </div>
                        </div>
                    {/each}
                </div>
            </div>
        </div>
    </div>
</div>

<style>
    .accordion {
        width: 100%;
        margin-top: 20px;
    }

    .panel {
        border: 1px solid #ccc;
        border-radius: 5px;
        margin-bottom: 10px;
    }

    .header {
        background-color: #f0f0f0;
        padding: 10px;
        cursor: pointer;
    }

    .content {
        padding: 10px;
        display: none;
    }

    .panel.active .content {
        display: block;
    }

    .container-title {
        display: flex;
        align-items: center;
    }

    .name {
        margin-right: 10px;
    }

    .number {
        margin-left: auto;
    }

    .accordion-container {
        width: 100%;
    }

    .image-container {
        width: 100%;
        justify-content: center;
        align-items: center;
        margin-top: 20px;
    }

    .image-container img {
        max-width: 100%;
        max-height: 50vh;
        margin: 10px;
        margin: 10px auto;
        display: block;
        border: green solid 2px;
    }
</style>
