<script>
    // @ts-nocheck

    import SearchBar from "$lib/components/SearchBar.svelte";
    import { onMount } from "svelte";
    import { writable } from "svelte/store";
    import data from "$lib/data/user_info.json"; // Assuming data.json is the file containing your JSON data

    // Store the selected user
    export const selectedUser = writable(null);

    /**
     * @type {Array<{
     *    serialNumber: number,
     *    userId: number,
     *    userName: string,
     *    role: string,
     *    email: string,
     *    status: string,
     *    dateRegistered: string
     * }>}
     */
    let users = [];

    onMount(() => {
        users = data;
    });

    /**
     * @type {{ serialNumber: number; userId: number; userName: string; role: string; email: string; status: string; dateRegistered: string; } | null}
     */
    let editingUser = null;

    /**
     * @param {{ serialNumber: number; userId: number; userName: string; role: string; email: string; status: string; dateRegistered: string; } | null} user
     */
    function viewUserDetail(user) {
        if (editingUser !== user) {
            // @ts-ignore
            selectedUser.set(user);
            editingUser = user;
        } else {
            selectedUser.set(null);
            editingUser = null;
        }
    }
    function saveUserChanges() {
        // Save user changes here if needed
        selectedUser.set(null);
        editingUser = null;
    }
</script>

<div>
    <SearchBar />
</div>
<table>
    <thead>
        <tr>
            <th>Serial Number</th>
            <th>userId</th>
            <th>userName</th>
            <th>role</th>
            <th>email</th>
            <th>status</th>
            <th>dateRegistered</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        {#each users as user}
            <tr>
                <td>{user.serialNumber}</td>
                <td>{user.userId}</td>
                <td>
                    {#if editingUser === user}
                        <input type="text" bind:value={user.userName} />
                    {:else}
                        {user.userName}
                    {/if}
                </td>
                <td>
                    {#if editingUser === user}
                        <input type="text" bind:value={user.role} />
                    {:else}
                        {user.role}
                    {/if}
                </td>
                <td>
                    {#if editingUser === user}
                        <input type="text" bind:value={user.email} />
                    {:else}
                        {user.email}
                    {/if}
                </td>
                <td>
                    {#if editingUser === user}
                        <input type="text" bind:value={user.status} />
                    {:else}
                        {user.status}
                    {/if}
                </td>
                <td>{user.dateRegistered}</td>
                <td>
                    {#if editingUser === user}
                        <button on:click={() => saveUserChanges(user)}
                            >Update</button
                        >
                    {:else}
                        <button on:click={() => viewUserDetail(user)}
                            >Edit</button
                        >
                    {/if}
                </td>
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
