<script>
    // // @ts-nocheck
    import SearchBar from "$lib/components/SearchBar.svelte";
    import { onMount } from "svelte";
    import { get } from "svelte/store";
    import { defaultApi } from '$lib/store'; // Import the defaultApi store

    /** @typedef {import("$lib/generated/models/UsersDetail").UsersDetail} UsersDetail */

    /**@type {UsersDetail}*/
    export let data;

    onMount(() => {
        console.log("Loading data");
    });
    console.log(data);

    let editingUser = null;

    // Function to toggle edit mode for a user
    function toggleEditMode(user) {
        editingUser = editingUser === user ? null : user;
    }

    /**
     * @description Update user details on the server.
     * @param {import('$lib/generated').UserDetailUpdate} updatedUser - The updated user details.
     * @returns {Promise<void>}
     */
    export async function updateUser(updatedUser) {
        try {
            // Send a request to the server to update user details using the generated OpenAPI client
            await get(defaultApi).userDetailUpdate({
                userDetailUpdate: updatedUser,
            });
            console.log("User details updated successfully.");
        } catch (error) {
            console.error("Failed to update user details:", error);
        }
    }

    // Function to save user changes
    async function saveUserChanges(user) {
        try {
            console.log(user);
            54;
            // Call the updateUser function to update user details on the server
            await updateUser(user);
            console.log("User details saved successfully.");
        } catch (error) {
            console.error("Failed to save user details:", error);
        }
        editingUser = null;
    }
</script>

<div>
    <SearchBar />
</div>
<table>
    <thead>
        <tr>
            <!-- <th>Serial Number</th> -->
            <th>User ID</th>
            <th>User Name</th>
            <th>Role</th>
            <th>Email</th>
            <th>Status</th>
            <th>Date Registered</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        {#each data.users as user}
            <tr>
                <!-- <td>{user.serialNumber}</td> -->
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
                        <button on:click={() => toggleEditMode(user)}
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
