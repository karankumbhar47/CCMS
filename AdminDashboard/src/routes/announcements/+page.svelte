<script>
    import SearchBar from "$lib/components/SearchBar.svelte";
    import { onMount } from "svelte";
    import { get, writable } from "svelte/store";
    import { getDate, getTime } from "$lib/utils/dateTime";
    import { getDefaultApi } from "$lib/utils/auth";

    /**@typedef {import("$lib/generated").Announcement} Announcement */

    /** @type {Array.<Announcement>} announcements */
    let announcements = [];

    /** @type import('svelte/store').Writable.<number> */
    const currentIndex = writable(-1);

    /**
     * Enum for current State
     * @enum {string}
     */
    let SidebarStateEnum = {
        /** The true value */
        New: "new",
        Update: "update",
        View: "view",
        Wait: "wait",
    };

    /** @type {SidebarStateEnum} */
    let sidebarState = SidebarStateEnum.New;

    /** @type {string | undefined} */
    let title = "";
    /** @type {string | undefined} */
    let description = "";
    /** @type {string | undefined} */
    let dt = "";

    async function newAnnouncement() {
        sidebarState = SidebarStateEnum.Wait;

        try {
            let res = await getDefaultApi().newAnnouncement({
                newAnnouncement: {
                    title: title,
                    description: description,
                    time: dt,
                },
            });
            if (res === true) {
                title = "";
                description = "";
                dt = "";
            }
            sidebarState = SidebarStateEnum.New;
        } catch {
            sidebarState = SidebarStateEnum.New;
        }
    }

    async function updateAnnouncement() {
        sidebarState = SidebarStateEnum.Wait;

        try {
            let res = await getDefaultApi().updateAnnouncement({
                announcement: {
                    id: announcements[get(currentIndex)].id,
                    title: title,
                    description: description,
                    time: dt,
                },
            });
            if (res === true) {
                sidebarState = SidebarStateEnum.View;
                console.log("Announcement Update Sucessful");
            } else {
                sidebarState = SidebarStateEnum.Update;
            }
        } catch {
            sidebarState = SidebarStateEnum.Update;
        }
    }

    function cancelEdit() {
        let index = get(currentIndex);
        sidebarState = SidebarStateEnum.View;
        title = announcements[index].title;
        description = announcements[index].description;
        dt = announcements[index].time;
    }

    function startEdit() {
        sidebarState = SidebarStateEnum.Update;
    }
    function startNew() {
        currentIndex.set(-1);
        sidebarState = SidebarStateEnum.New;
    }

    /**
     * @param {number} index
     */
    function viewAnnouncement(index) {
        currentIndex.set(index);
        sidebarState = SidebarStateEnum.View;
    }

    onMount(async () => {
        try {
            announcements = await getDefaultApi().getAnnouncements();
        } catch (e) {
            console.log("Failed to fetch announcements");
            console.log(e);
        }

        currentIndex.subscribe((index) => {
            if (index != -1) {
                title = announcements[index].title;
                description = announcements[index].description;
                dt = announcements[index].time;
            } else {
                title = "";
                description = "";
                dt = "";
            }
        });
    });
</script>

<div class="container">
    <div class="announcements-table">
        <div>
            <SearchBar />
        </div>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Date</th>
                    <th>Time</th>
                    <th>Title</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                {#each announcements as announcement, index}
                    <tr>
                        <td>{announcement.id}</td>
                        <td>{getDate(announcement.time)}</td>
                        <td>{getTime(announcement.time)}</td>
                        <td>{announcement.title}</td>
                        <td>
                            <button
                                on:click={() => {
                                    viewAnnouncement(index);
                                }}
                            >
                                <a href={`/announcements/`}> View </a>
                            </button>
                        </td>
                    </tr>
                {/each}
            </tbody>
        </table>
    </div>
    <div class="announcement-sidebar">
        {#if sidebarState == SidebarStateEnum.New}
            <table>
                <thead>
                    <tr>
                        <th colspan="2"><center> New Announcement </center></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>
                            <label for="new-title">Title</label>
                        </td>
                        <td>
                            <input
                                name="new-title"
                                placeholder="Title"
                                bind:value={title}
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="new-description">Description</label>
                        </td>
                        <td>
                            <input
                                name="new-description"
                                placeholder="Description"
                                bind:value={description}
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="new-date">Date</label>
                        </td>
                        <td>
                            <input
                                name="new-description"
                                type="datetime-local"
                                bind:value={dt}
                            />
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <button on:click={newAnnouncement}>Submit</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        {:else if sidebarState == SidebarStateEnum.Update}
            <table>
                <thead>
                    <tr>
                        <th colspan="2"><center> Edit Announcement </center></th
                        >
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>
                            <label for="new-title">Title</label>
                        </td>
                        <td>
                            <input
                                name="new-title"
                                placeholder="Title"
                                bind:value={title}
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="new-description">Description</label>
                        </td>
                        <td>
                            <input
                                name="new-description"
                                placeholder="Description"
                                bind:value={description}
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="new-date">Date</label>
                        </td>
                        <td>
                            <input
                                name="new-description"
                                type="datetime-local"
                                placeholder="YYYY-MM-DD"
                                bind:value={dt}
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <button on:click={cancelEdit}>Cancel</button>
                        </td>
                        <td>
                            <button on:click={updateAnnouncement}>Update</button
                            >
                        </td>
                    </tr>
                </tbody>
            </table>
        {:else}
            <table>
                <thead>
                    <tr>
                        <th colspan="2"><center> Announcement </center></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>
                            <label for="new-title">Title</label>
                        </td>
                        <td>{title}</td>
                    </tr>
                    <tr>
                        <td>
                            <label for="new-description">Description</label>
                        </td>
                        <td>
                            {description}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="new-date">Date</label>
                        </td>
                        <td>
                            {dt}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <button on:click={startNew}>New</button>
                        </td>
                        <td>
                            <button on:click={startEdit}>Update</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        {/if}
    </div>
</div>

<style>
    .container {
        display: flex;
        flex-flow: row;
        margin: 5%;
    }
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

    .announcement-sidebar {
        margin: 5%;
        font-size: large;
    }
</style>
