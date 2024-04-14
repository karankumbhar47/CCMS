<script>
    import Header from "$lib/components/Header.svelte";
    import Sidebar from "$lib/components/Sidebar.svelte";
    import { onMount } from "svelte";
    import Cookies from "js-cookie";
    import { page } from "$app/stores";
    import { goto } from "$app/navigation";

    let userName = "User Name"; // Replace with the actual admin name

    onMount(() => {
        /** type {string | undefined} token */
        const token = Cookies.get("StudentPortalAuthToken");
        console.log($page.url.pathname);

        if (token === undefined && $page.url.pathname !== "/login") {
            goto("/login");
        }
    });
</script>

<div class="container">
    <div class="sider">
        <Sidebar />
    </div>
    <div class="header">
        <Header {userName} />
    </div>
    <div class="content">
        <slot />
    </div>
</div>

<style>
    /* Add your main container styling here */
    .sider {
        position: fixed;
        z-index: 10;
        display: block;
    }

    .content {
        flex: 1;
        overflow: auto;
        margin-left: 250px; /* Adjust the margin based on sidebar width */
        padding: 20px;
        position: absolute;
        top: 120px;
    }

    .container {
        z-index: 5;
        display: block;
        height: 100vh;
        overflow: auto;
        position: relative;
    }
    .header {
        top: 0;
        left: 0;
        right: 0;
        display: block;
        width: 100vw;
        position: fixed;
        z-index: 1000;
    }
</style>
