<script>
    import Accordion, { Panel, Header, Content } from "@smui-extra/accordion";

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
</script>

<div class="image-container">
    <Accordion>
        <Panel>
            <Header>
                <div class="container-title">
                    <div class="name">{title}</div>
                    <div class="number">{number}</div>
                </div>
            </Header>
            <Content>
                <div class="accordion-container">
                    <Accordion>
                        {#each fileList as file}
                            <Panel>
                                <Header>{file.fileId}</Header>
                                <Content>
                                    <!-- svelte-ignore a11y-img-redundant-alt -->
                                    <img src={file.imageUrl} alt="Image" />
                                </Content>
                            </Panel>
                        {/each}
                    </Accordion>
                </div>
            </Content>
        </Panel>
    </Accordion>
</div>

<style>
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
