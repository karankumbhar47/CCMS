<script>
    /** @type {Array.<{name: string; url:string;}>} */
    let links = [
        { name: "Announcements", url: "/announcements" },
        { name: "New Complain", url: "/complain/new" },
        { name: "View Complains", url: "/complain/view" },
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

<style lang="scss">
    .sidebar div {
        display: block;
        background: none;
        border: none;
        padding: 30px 20px;
        font-size: large;
        cursor: pointer;
        text-align: left;
    }

    .sidebar {
        background-color: #2c3e50;
        padding-top: 120px;
        height: 100vh;
        position: absolute;
        width: 250px;
        position: fixed;
        top: 0;
        left: 0;
        bottom: 0;
        display: flex;
        flex-direction: column;
    }

    .sidebar-heading {
        color: #ffffff;
        padding: 20px;
        cursor: pointer;
    }

    .sidebar-heading:hover {
        background-color: #34495e;
    }
</style>
