<script>
    import DataTable, {
        Head,
        Body,
        Row,
        Cell,
        Pagination,
    } from "@smui/data-table";
    import List, { Item, Meta } from "@smui/list";
    import Checkbox from "@smui/checkbox";
    import Select, { Option } from "@smui/select";
    import IconButton from "@smui/icon-button";
    import { Label } from "@smui/common";

    import SearchBar from "$lib/components/SearchBar.svelte";
    import { getDefaultApi } from "$lib/utils/auth";
    import { onMount } from "svelte";
    import { Role, UserInfoStatusEnum } from "$lib/generated";

    /** @typedef {import("$lib/generated").UserInfo} UserInfo */

    let rowsPerPage = 10;
    /** @type {UserInfo[]} */
    let users = [];
    /** @type {number} */
    let page = 1;
    /** @type {number} */
    let totalPages = 0;
    /** @type {boolean} */
    let isLoading = false;

    onMount(async () => {
        await loadUsers();
    });

    async function loadUsers() {
        try {
            isLoading = true;
            console.log(page);
            const response = await getDefaultApi().getUsers({
                page: page,
                size: 10,
            });
            totalPages = response.totalPages ? response.totalPages : 1;
            users = response.users ? response.users : [];
            isLoading = false;
        } catch (error) {
            console.error("Failed to fetch user details:", error);
        } finally {
            isLoading = false;
        }
        console.log(users);
    }

    /** @type {UserInfo | null} */
    let editingUser = null;
    /** @type {boolean} */
    let isAddingUser = false;

    /**
     * @param {UserInfo | null} user
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
     * @param {UserInfo} user
     */
    async function saveUserChanges(user) {
        try {
            console.log(user);
            await getDefaultApi().userDetailUpdate({
                userInfo: user,
            });
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
                name: "",
                roles: role,
                email: email,
                status: status,
                dateRegistered: dateRegistered,
                department: "",
                phoneNumber: "",
            };

            await getDefaultApi().createUser({
                userInfo: newUser,
            });
            console.log("User created successfully.");
            closeAddUserModal();
        } catch (error) {
            console.error("Failed to create user:", error);
        }
    }

    /**
     * @param {UserInfo} user
     */
    async function deleteUser(user) {
        try {
            await getDefaultApi().deleteUser({
                deleteUserRequest: { userId: user.userId },
            });
            console.log("User deleted successfully.");
        } catch (error) {
            console.error("Failed to delete user:", error);
        }
    }

    async function nextPage() {
        if (page < totalPages) {
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
            <button on:click={createUser} class="btn btn-primary"
                >Add user</button
            >
        </div>
    </div>
{/if}

<DataTable table$aria-label="Todo list" style="width: 100%;">
    <Head>
        <Row>
            <Cell>User ID</Cell>
            <Cell>Username</Cell>
            <Cell>Name</Cell>
            <Cell>Role</Cell>
            <Cell>Email</Cell>
            <Cell>Status</Cell>
            <Cell>Date Registered</Cell>
            <Cell>Department</Cell>
            <Cell>Phone</Cell>
            <Cell>Action</Cell>
            <Cell>Delete</Cell>
        </Row>
    </Head>
    <Body>
        {#each users as user}
            <Row>
                <Cell>{user.userId}</Cell>
                <Cell>
                    {#if editingUser === user}
                        <input
                            type="text"
                            bind:value={user.userName}
                            class="form-control"
                        />
                    {:else}
                        {user.userName}
                    {/if}
                </Cell>
                <Cell>
                    {#if editingUser === user}
                        <input
                            type="text"
                            bind:value={user.name}
                            class="form-control"
                        />
                    {:else}
                        {user.name}
                    {/if}
                </Cell>
                <Cell>
                    {#if editingUser === user}
                        <List class="demo-list" checkList>
                            {#each Object.keys(Role) as role}
                                <Item>
                                    <Label>{role}</Label>
                                    <Meta>
                                        <Checkbox
                                            bind:group={user.roles}
                                            value={role}
                                        />
                                    </Meta>
                                </Item>
                            {/each}
                        </List>
                    {:else if user.roles}
                        {#each user.roles as role, i}
                            {#if i != 0},
                            {/if}
                            {role}
                        {/each}
                    {/if}
                </Cell>
                <Cell>
                    {#if editingUser === user}
                        <input
                            type="text"
                            bind:value={user.email}
                            class="form-control"
                        />
                    {:else}
                        {user.email}
                    {/if}
                </Cell>
                <Cell>
                    {#if editingUser === user}
                        <Select bind:value={user.status} label="Status">
                            {#each Object.values(UserInfoStatusEnum) as stat}
                                <Option value={stat}>{stat}</Option>
                            {/each}
                        </Select>
                    {:else}
                        {user.status}
                    {/if}
                </Cell>
                <Cell>{user.dateRegistered.toLocaleDateString('en-US', { year: 'numeric', month: '2-digit', day: '2-digit' })}</Cell>
                <Cell>
                    {#if editingUser === user}
                        <input
                            type="text"
                            bind:value={user.department}
                            class="form-control"
                        />
                    {:else}
                        {user.department}
                    {/if}
                </Cell>
                <Cell>
                    {#if editingUser === user}
                        <input
                            type="text"
                            bind:value={user.phoneNumber}
                            class="form-control"
                        />
                    {:else}
                        {user.phoneNumber}
                    {/if}
                </Cell>
                <Cell>
                    {#if editingUser === user}
                        <button
                            on:click={() => saveUserChanges(user)}
                            class="btn btn-success">Update</button
                        >
                    {:else}
                        <button
                            on:click={() => toggleEditMode(user)}
                            class="btn btn-primary">Edit</button
                        >
                    {/if}
                </Cell>
                <Cell>
                    <button
                        on:click={() => deleteUser(user)}
                        class="btn btn-danger">Delete</button
                    >
                </Cell>
            </Row>
        {/each}
    </Body>
    <Pagination slot="paginate">
        <svelte:fragment slot="rowsPerPage">
            <Label>Rows Per Page</Label>
            <Select variant="outlined" bind:value={rowsPerPage} noLabel>
                <Option value={10}>10</Option>
                <Option value={25}>25</Option>
                <Option value={100}>100</Option>
            </Select>
        </svelte:fragment>
        <svelte:fragment slot="total">
            <!-- {start + 1}-{end} of {users.length} -->
        </svelte:fragment>

        <IconButton
            class="material-icons"
            action="first-page"
            title="First page"
            on:click={() => (page = 1)}
            disabled={page === 1}>first_page</IconButton
        >
        <IconButton
            class="material-icons"
            action="prev-page"
            title="Prev page"
            on:click={previousPage}
            disabled={page === 1}>chevron_left</IconButton
        >
        <IconButton
            class="material-icons"
            action="next-page"
            title="Next page"
            on:click={nextPage}
            disabled={page === totalPages}>chevron_right</IconButton
        >
        <IconButton
            class="material-icons"
            action="last-page"
            title="Last page"
            on:click={() => (page = totalPages)}
            disabled={page === totalPages}>last_page</IconButton
        >
    </Pagination>
</DataTable>

<div class="buttons">
    <button on:click={openAddUserModal} class="btn btn-primary">Add User</button>
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
