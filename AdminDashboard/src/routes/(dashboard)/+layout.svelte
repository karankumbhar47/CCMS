<script>
    import Header from "$lib/components/Header.svelte";
    import Sidebar from "$lib/components/Sidebar.svelte";
    let adminName = "Admin Name"; // Replace with the actual admin name
</script>

<div class="container">
    <div class="sider">
        <Sidebar />
    </div>
    <div class="header">
        <Header {adminName} />
    </div>
    <div class="content">
        <slot />
    </div>
</div>

<style>
    /* Add your main container styling here */
    .sider{
        position: sticky;
        z-index: 10;
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
        overflow: auto; /* Allow scrolling */
        position: relative; /* Ensure correct stacking context */
    }
    .header {
        top: 0;
        left: 0;
        right: 0;
        display: block;
        width: 100vw;
        position:fixed;
        z-index: 1000;
    }
</style>
