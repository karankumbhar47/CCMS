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
    import Dialog, { Header, Title, Content, Actions } from "@smui/dialog";
    import Button, { Label } from "@smui/button";
    import LinearProgress from "@smui/linear-progress";
    import Textfield from "@smui/textfield";
    import HelperText from "@smui/textfield/helper-text";

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
    let loaded = true;

    onMount(async () => {
        await loadUsers();
    });

    async function loadUsers() {
        try {
            loaded = false;
            console.log(page);
            const response = await getDefaultApi().getUsers({
                page: page,
                size: 10,
            });
            totalPages = response.totalPages ? response.totalPages : 1;
            users = response.users ? response.users : [];
            loaded = true;
        } catch (error) {
            console.error("Failed to fetch user details:", error);
        } finally {
            loaded = true;
        }
        console.log(users);
    }

    /** @type {UserInfo | null} */
    let editingUser = null;
    /** @type {UserInfo} */
    let addingUser = {
        userId: "",
        userName: "",
        name: "",
        roles: [],
        email: "",
        status: UserInfoStatusEnum.Active,
        password: "1234",
        dateRegistered: new Date(),
        department: "",
        phoneNumber: "",
    };
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

    /** @param {CustomEvent<{action: string }} e  */
    async function closeAddUserModal(e) {
        switch (e.detail.action) {
            case "cancel": {
                addingUser = {
                    userId: "",
                    userName: "",
                    name: "",
                    roles: [],
                    email: "",
                    status: UserInfoStatusEnum.Active,
                    password: "1234",
                    dateRegistered: new Date(),
                    department: "",
                    phoneNumber: "",
                };
                break;
            }
            case "add": {
                // check all the field of addingUser
                if (addingUser.userName === "" && addingUser.userName === "") {
                    isAddingUser = true;
                    return;
                }
                try {
                    await getDefaultApi().createUser({
                        userInfo: addingUser,
                    });
                    console.log("User created successfully.");
                } catch (error) {
                    console.error("Failed to create user:", error);
                }
                addingUser = {
                    userId: "",
                    userName: "",
                    name: "",
                    roles: [],
                    email: "",
                    status: UserInfoStatusEnum.Active,
                    password: "1234",
                    dateRegistered: new Date(),
                    department: "",
                    phoneNumber: "",
                };
                break;
            }
        }
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

<Dialog
    bind:open={isAddingUser}
    scrimClickAction=""
    escapeKeyAction=""
    aria-labelledby="fullscreen-title"
    aria-describedby="fullscreen-content"
    on:SMUIDialog:closed={closeAddUserModal}
>
    <Header>
        <Title id="fullscreen-title">Add New User</Title>
    </Header>
    <Content id="fullscreen-content">
        <DataTable table$aria-label="People list" style="max-width: 100%;">
            <Body>
                <Row>
                    <Cell>
                        <Textfield
                            bind:value={addingUser.userId}
                            label="ID Number"
                        >
                            <HelperText slot="helper">ID Number</HelperText>
                        </Textfield>
                    </Cell>
                </Row>
                <Row>
                    <Textfield
                        bind:value={addingUser.userName}
                        label="Username"
                    >
                        <HelperText slot="helper">Username</HelperText>
                    </Textfield>
                </Row>
                <Row>
                    <Textfield bind:value={addingUser.name} label="Name">
                        <HelperText slot="helper">Name</HelperText>
                    </Textfield>
                </Row>
                <Row>
                    <List class="demo-list" checkList>
                        {#each Object.keys(Role) as role}
                            <Item>
                                <Label>{role}</Label>
                                <Meta>
                                    <Checkbox
                                        bind:group={addingUser.roles}
                                        value={role}
                                    />
                                </Meta>
                            </Item>
                        {/each}
                    </List>
                </Row>
                <Row>
                    <Textfield
                        bind:value={addingUser.email}
                        label="Email"
                        type="email"
                    >
                        <HelperText slot="helper">Email:</HelperText>
                    </Textfield>
                </Row>
                <Row>
                    <Select bind:value={addingUser.status} label="Status">
                        {#each Object.values(UserInfoStatusEnum) as stat}
                            <Option value={stat}>{stat}</Option>
                        {/each}
                    </Select>
                </Row>
                <Row>
                    <Textfield
                        bind:value={addingUser.dateRegistered}
                        label="Registration date"
                        type="date"
                    >
                        <HelperText slot="helper">Registration date</HelperText>
                    </Textfield>
                </Row>
            </Body>
        </DataTable>
    </Content>
    <Actions>
        <Button action="cancel">
            <Label>Cancel</Label>
        </Button>
        <Button action="add" defaultAction>
            <Label>Add User</Label>
        </Button>
    </Actions>
</Dialog>

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
                <Cell
                    >{user.dateRegistered.toLocaleDateString("en-US", {
                        year: "numeric",
                        month: "2-digit",
                        day: "2-digit",
                    })}</Cell
                >
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
    <LinearProgress
        indeterminate
        bind:closed={loaded}
        aria-label="Data is being loaded..."
        slot="progress"
    />
</DataTable>

<div class="buttons">
    <button on:click={openAddUserModal} class="btn btn-primary">Add User</button
    >
</div>

<style>
    /* CSS Styles */
    .modal {
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
