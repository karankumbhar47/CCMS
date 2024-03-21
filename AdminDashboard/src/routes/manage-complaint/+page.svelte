<script>
    import SearchBar from "$lib/components/SearchBar.svelte";
    import { onMount } from "svelte";
    import { writable } from "svelte/store";
    import data from "$lib/data/complain_info.json"; // Assuming data.json is the file containing your JSON data

    // Store the selected complaint
    export const selectedComplaint = writable(null);

    /**
     * @type {Array<{
     *    serialNumber: number,
     *    complain: string,
     *    tags: string[],
     *    severity: string,
     *    location: string,
     *    complainer: { id: number, name: string }
     * }>}
     */
    let complaints = [];

    onMount(() => {
        complaints = data;
    });

    /**
     * @param {{ serialNumber: number; complain: string; tags: string[]; severity: string; location: string; complainer: { id: number; name: string; }; } | null} complaint
     */
    function viewComplaint(complaint) {
        // @ts-ignore
        selectedComplaint.set(complaint);
    }
</script>

<div>
    <SearchBar />
</div>
<table>
    <thead>
        <tr>
            <th>Serial Number</th>
            <th>Complain</th>
            <th>Tags</th>
            <th>Severity</th>
            <th>Location</th>
            <th>Complainer</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        {#each complaints as complaint}
            <tr>
                <td>{complaint.serialNumber}</td>
                <td>{complaint.complain}</td>
                <td>{complaint.tags.join(", ")}</td>
                <td>{complaint.severity}</td>
                <td>{complaint.location}</td>
                <td>{complaint.complainer.name}</td>
                <td
                    ><button on:click={() => viewComplaint(complaint)}
                        >View</button
                    ></td
                >
            </tr>
        {/each}
    </tbody>
</table>

<style>
    /* Add your table styling here */
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
</style>
