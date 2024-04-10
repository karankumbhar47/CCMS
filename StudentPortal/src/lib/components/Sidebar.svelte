<!-- Sidebar.svelte -->

<script>
    /** @type Array.<{name: string; url:string;}> */
    let links = [
        { name: "Announcements", url: "/announcements" },
        { name: "New Complain", url: "/complain/new" },
        { name: "View Complains", url: "/complain" },
        { name: "Logout", url: "/logout" },
    ];
</script>

<div class="sidebar" role="navigation">
    {#each links as link}
        <a class="sidebar-heading" href={link.url}>
            {link.name}
        </a>
    {/each}
</div>

<style lang="scss">
    .sidebar {
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
        padding-left: 20px;
        padding-top: 20px;
        padding-bottom: 20px;
        margin: 0px;
        cursor: pointer;
    }

    .sidebar-heading:hover {
        background-color: #34495e; /* Highlight color on hover/active */
    }
</style>
