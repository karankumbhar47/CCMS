<script>
    let links = [
        { name: "Overview", url: "/" },
        { name: "Manage User", url: "/manage-user" },
        { name: "Manage Complaint", url: "/manage-complaint" },
        { name: "Manage Announcement", url: "/announcements" },
        { name: "System Configuration", url: "/logout" },
        { name: "Logout", url: "/logout" },
    ];
</script>

<div class="sidebar" role="navigation">
    {#each links as link}
        <a href={link.url}>
            <div class="sidebar-heading">
                {link.name}
            </div>
        </a>
    {/each}
</div>

<style>
    .sidebar div {
        display: block;
        background: none;
        border: none;
        padding: 30px 20px;
        margin: 0;
        font-size: large;
        cursor: pointer;
        text-align: left;
    }

    .sidebar {
        background-color: #2c3e50; /* Sidebar background color */
        padding-top: 120px;
        height: 100vh;
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
