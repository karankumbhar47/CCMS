<script>
    // @ts-nocheck

    import SearchBar from "$lib/components/SearchBar.svelte";
    import { getDefaultApi } from "$lib/utils/auth";
    import { onMount } from "svelte";

    /** @typedef {import("$lib/generated/models/UsersDetail").UsersDetail} UsersDetail */

    /**@type {Array.<UsersDetail>}*/
    let users = [];

    onMount(async () => {
        console.log("Loading data");
        try {
            // Fetch user details from the server using the generated OpenAPI client
            users = await getDefaultApi().usersDetail();
        } catch (error) {
            console.error("Failed to fetch user details:", error);
        }
        console.log(users);
    });

    /**
     * @type {null}
     */
    let editingUser = null;
    let isAddingUser = false;

    // Function to toggle edit mode for a user

    /**
     * @param {null} user
     */
    function toggleEditMode(user) {
        editingUser = editingUser === user ? null : user;
    }

    function openAddUserModal() {
        isAddingUser = true;
    }

    function closeAddUserModal() {
        isAddingUser = false;
    }
    /**
     * @description Update user details on the server.
     * @param {import('$lib/generated').UserDetailUpdate} newUser - The updated user details.
     */
    function handleSaveUser(newUser) {
        console.log("New user:", newUser);
        // Send a request to the server to save the new user
        // You can use the generated OpenAPI client to call the API endpoint here
        // After saving the user, close the modal
        closeAddUserModal();
    }

    /**
     * @description Update user details on the server.
     * @param {import('$lib/generated').UserDetailUpdate} updatedUser - The updated user details.
     * @returns {Promise<void>}
     */
    async function updateUser(updatedUser) {
        try {
            // Send a request to the server to update user details using the generated OpenAPI client
            await getDefaultApi().userDetailUpdate({
                userDetailUpdate: updatedUser,
            });
            console.log("User details updated successfully.");
        } catch (error) {
            console.error("Failed to update user details:", error);
        }
    }

    // Function to save user changes
    /**
     * @param {import("$lib/generated").UserDetailUpdate} user
     */
    async function saveUserChanges(user) {
        try {
            console.log(user);
            // Call the updateUser function to update user details on the server
            await updateUser(user);
            console.log("User details saved successfully.");
        } catch (error) {
            console.error("Failed to save user details:", error);
        }
        editingUser = null;
    }

    // Function to handle the creation of a new user
    async function createUser() {
        try {
            // Get user input from the input fields
            const userId = document.getElementById("userId").value;
            const userName = document.getElementById("userName").value;
            const role = document.getElementById("role").value;
            const email = document.getElementById("email").value;
            const status = document.getElementById("status").value;
            const dateRegistered =
                document.getElementById("dateRegistered").value;

            // Prepare user object
            const newUser = {
                userId: userId,
                userName: userName,
                role: role,
                email: email,
                status: status,
                dateRegistered: dateRegistered,
            };

            // Send a request to the server to create a new user using the generated OpenAPI client
            await getDefaultApi().createUser({
                userDetailUpdate: newUser,
            });
            console.log("User created successfully.");
            closeAddUserModal();

            // Dispatch an event to inform the parent component about the user creation
            // dispatch("userCreated");
        } catch (error) {
            console.error("Failed to create user:", error);
        }
    }

    async function deleteUser(user) {
        try {
            // Send a request to delete the user
            await get(defaultApi).deleteUser({ deleteUserRequest: user });
            console.log("User deleted successfully.");
        } catch (error) {
            console.error("Failed to delete user:", error);
        }
    }
</script>

<SearchBar />

{#if isAddingUser}
    <div class="modal">
        <div class="modal-content">
            <span class="close" on:click={closeAddUserModal}>&times;</span>
            <div>
                <label for="userId">User ID:</label>
                <input type="text" id="userId" />
            </div>
            <div>
                <label for="userName">User Name:</label>
                <input type="text" id="userName" />
            </div>
            <div>
                <label for="role">Role:</label>
                <input type="text" id="role" />
            </div>
            <div>
                <label for="email">Email:</label>
                <input type="text" id="email" />
            </div>
            <div>
                <label for="status">Status:</label>
                <input type="text" id="status" />
            </div>
            <div>
                <label for="dateRegistered">Date Registered:</label>
                <input type="text" id="dateRegistered" />
            </div>
            <button on:click={createUser}>Add user</button>
        </div>
    </div>
{/if}

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
            <th>Delete</th>
        </tr>
    </thead>
    <tbody>
        {#each users as user}
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
                        <select bind:value={user.role}>
                            <option value="admin">admin</option>
                            <option value="resolver">resolver</option>
                            <option value="student">student</option>
                        </select>
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
                        <select bind:value={user.status}>
                            <option value="active">active</option>
                            <option value="block">block</option>
                        </select>
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
                <td>
                    <button on:click={() => deleteUser(user)}>Delete</button>
                </td>
            </tr>
        {/each}
    </tbody>
</table>

<div>
    <button on:click={openAddUserModal}>Add User</button>
</div>

<style>
    table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 20px;
    }
    th,
    td {
        padding: 8px;
        border: 1px solid #ddd;
        text-align: left;
    }
    th {
        background-color: #f2f2f2;
    }

    label {
        display: block;
        margin-top: 10px;
    }
    input[type="text"] {
        width: 100%;
        padding: 8px;
        margin-top: 5px;
        margin-bottom: 10px;
        box-sizing: border-box;
    }
    button {
        background-color: #4caf50;
        color: white;
        padding: 10px 20px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }
    button:hover {
        background-color: #45a049;
    }

    .modal {
        display: block;
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgb(0, 0, 0);
        background-color: rgba(0, 0, 0, 0.4);
    }

    .modal-content {
        background-color: #fefefe;
        margin: 15% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 80%;
    }

    .close {
        color: #aaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
    }

    .close:hover,
    .close:focus {
        color: black;
        text-decoration: none;
        cursor: pointer;
    }
</style>
