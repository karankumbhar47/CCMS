<script>
    import SearchBar from "$lib/components/SearchBar.svelte";
    import { getDefaultApi } from "$lib/utils/auth";
    import { onMount } from "svelte";

    let users = [];
    let page = 1; // Initial page number
    let totalPages = 0;
    let isLoading = false;

    onMount(async () => {
        console.log("Loading data");
        await loadUsers();
    });

    async function loadUsers() {
        try {
            console.log("loading 1");
            isLoading = true;
            console.log(page);
            console.log("loading 2");
            const response = await getDefaultApi().getUsers({
                page: page,
                size: 10,
            });
            console.log("loading 3");
            totalPages = response.totalPages ? response.totalPages : 1;
            console.log("totalPages:", totalPages);
            users = response.users ? response.users : [];
        } catch (error) {
            console.error("Failed to fetch user details:", error);
        } finally {
            isLoading = false;
        }
        console.log(users);
    }

    let editingUser = null;
    let isAddingUser = false;

    function toggleEditMode(user) {
        editingUser = editingUser === user ? null : user;
    }

    function openAddUserModal() {
        isAddingUser = true;
    }

    function closeAddUserModal() {
        isAddingUser = false;
    }

    // /**
    //  * @description Update user details on the server.
    //  * @param {import('$lib/generated').UserDetailUpdate} newUser - The updated user details.
    //  */
    // function handleSaveUser(newUser) {
    //     console.log("New user:", newUser);
    //     // Send a request to the server to save the new user
    //     // You can use the generated OpenAPI client to call the API endpoint here
    //     // After saving the user, close the modal
    //     closeAddUserModal();
    // }

    async function updateUser(updatedUser) {
        try {
            await getDefaultApi().userDetailUpdate({
                userDetailUpdate: updatedUser,
            });
            console.log("User details updated successfully.");
        } catch (error) {
            console.error("Failed to update user details:", error);
        }
    }

    async function saveUserChanges(user) {
        try {
            console.log(user);
            await updateUser(user);
            console.log("User details saved successfully.");
        } catch (error) {
            console.error("Failed to save user details:", error);
        }
        editingUser = null;
    }

    async function createUser() {
        try {
            const userId = document.getElementById("userId").value;
            const userName = document.getElementById("userName").value;
            const role = document.getElementById("role").value;
            const email = document.getElementById("email").value;
            const status = document.getElementById("status").value;
            const dateRegistered =
                document.getElementById("dateRegistered").value;

            const newUser = {
                userId: userId,
                userName: userName,
                role: role,
                email: email,
                status: status,
                dateRegistered: dateRegistered,
            };

            await getDefaultApi().createUser({
                userDetailUpdate: newUser,
            });
            console.log("User created successfully.");
            closeAddUserModal();
        } catch (error) {
            console.error("Failed to create user:", error);
        }
    }

    async function deleteUser(user) {
        try {
            await getDefaultApi().deleteUser({ deleteUserRequest: user });
            console.log("User deleted successfully.");
        } catch (error) {
            console.error("Failed to delete user:", error);
        }
    }

    async function nextPage() {
        if (page < totalPages ) {
            page++;
            console.log("nextpage:", page);
            await loadUsers();
        }
    }

    async function previousPage() {
        if (page > 1) {
            page--;
            console.log("previouspage:", page);
            await loadUsers();
        }
    }
</script>

<SearchBar />

{#if isAddingUser}
    <div class="modal">
        <div class="modal-content">
            <!-- svelte-ignore a11y-click-events-have-key-events -->
            <!-- svelte-ignore a11y-no-static-element-interactions -->
            <span class="close" on:click={closeAddUserModal}>&times;</span>
            <div class="form-group">
                <label for="userId">User ID:</label>
                <input type="text" id="userId" class="form-control" />
            </div>
            <div class="form-group">
                <label for="userName">User Name:</label>
                <input type="text" id="userName" class="form-control" />
            </div>
            <div class="form-group">
                <label for="role">Role:</label>
                <input type="text" id="role" class="form-control" />
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="text" id="email" class="form-control" />
            </div>
            <div class="form-group">
                <label for="status">Status:</label>
                <input type="text" id="status" class="form-control" />
            </div>
            <div class="form-group">
                <label for="dateRegistered">Date Registered:</label>
                <input type="text" id="dateRegistered" class="form-control" />
            </div>
            <button on:click={createUser} class="btn btn-primary">Add user</button>
        </div>
    </div>
{/if}

<table class="table">
    <thead>
        <tr>
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
                <td>{user.userId}</td>
                <td>
                    {#if editingUser === user}
                        <input type="text" bind:value={user.userName} class="form-control" />
                    {:else}
                        {user.userName}
                    {/if}
                </td>
                <td>
                    {#if editingUser === user}
                        <select bind:value={user.role} class="form-control">
                            <option value="admin">Admin</option>
                            <option value="resolver">Resolver</option>
                            <option value="student">Student</option>
                        </select>
                    {:else}
                        {user.role}
                    {/if}
                </td>
                <td>
                    {#if editingUser === user}
                        <input type="text" bind:value={user.email} class="form-control" />
                    {:else}
                        {user.email}
                    {/if}
                </td>
                <td>
                    {#if editingUser === user}
                        <select bind:value={user.status} class="form-control">
                            <option value="active">Active</option>
                            <option value="block">Blocked</option>
                        </select>
                    {:else}
                        {user.status}
                    {/if}
                </td>
                <td>{user.dateRegistered}</td>
                <td>
                    {#if editingUser === user}
                        <button on:click={() => saveUserChanges(user)} class="btn btn-success">Update</button>
                    {:else}
                        <button on:click={() => toggleEditMode(user)} class="btn btn-primary">Edit</button>
                    {/if}
                </td>
                <td>
                    <button on:click={() => deleteUser(user)} class="btn btn-danger">Delete</button>
                </td>
            </tr>
        {/each}
    </tbody>
</table>

<div class="buttons">
    <button on:click={previousPage} class="btn btn-secondary">Previous</button>
    <button on:click={openAddUserModal} class="btn btn-primary">Add User</button>
    <button on:click={nextPage} class="btn btn-secondary">Next</button>
</div>

<style>
    /* CSS Styles */
    .modal {
        display: none;
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgba(0, 0, 0, 0.4);
    }

    .modal-content {
        background-color: #fefefe;
        margin: 15% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 80%;
        border-radius: 5px;
    }

    .close {
        color: #aaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
        cursor: pointer;
    }

    .close:hover,
    .close:focus {
        color: black;
        text-decoration: none;
    }

    .form-group {
        margin-bottom: 20px;
    }

    .table {
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

    .btn {
        padding: 8px 20px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    .btn-primary {
        background-color: #007bff;
        color: #fff;
    }

    .btn-primary:hover {
        background-color: #0056b3;
    }

    .btn-secondary {
        background-color: #6c757d;
        color: #fff;
    }

    .btn-secondary:hover {
        background-color: #5a6268;
    }

    .btn-success {
        background-color: #28a745;
        color: #fff;
    }

    .btn-success:hover {
        background-color: #218838;
    }

    .btn-danger {
        background-color: #dc3545;
        color: #fff;
    }

    .btn-danger:hover {
        background-color: #c82333;
    }

    .buttons {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 20px;
    }
</style>
