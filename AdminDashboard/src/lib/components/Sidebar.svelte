<!-- Sidebar.svelte -->

<script>
    import { goto } from "$app/navigation";
</script>

<div class="sidebar" role="navigation">
    <button class="sidebar-heading" on:click={() => goto("/")}>
        Overview
    </button>
    <button class="sidebar-heading" on:click={() => goto("/manage-user")}>
        Manage User
    </button>
    <button class="sidebar-heading" on:click={() => goto("/manage-complaint")}>
        Manage Complaint
    </button>
    <button
        class="sidebar-heading"
        on:click={() => goto("/manage-announcement")}
    >
        Manage Announcement
    </button>
    <button
        class="sidebar-heading"
        on:click={() => goto("/system-configuration")}
    >
        System Configuration
    </button>
    <button class="sidebar-heading" on:click={() => goto("/logout")}>
        Logout
    </button>
</div>

<style>
    .sidebar button {
        display: block;
        background: none;
        border: none;
        padding: 0;
        margin: 0;
        cursor: pointer;
        text-align: left;
    }

    .sidebar {
        background-color: #2c3e50; /* Sidebar background color */
        padding-top: 120px;
        padding-left: 20px;
        height: 100%;
        position: absolute;
        width: 250px; /* Adjust the width of the sidebar */
        position: fixed;
        top: 0;
        left: 0;
        bottom: 0;
        display: flex;
        flex-direction: column;
    }

    .sidebar-heading {
        color: #ffffff; /* Sidebar heading text color */
        padding: 20px;
        cursor: pointer;
    }

    .sidebar-heading:hover {
        background-color: #34495e; /* Highlight color on hover/active */
    }
</style>
