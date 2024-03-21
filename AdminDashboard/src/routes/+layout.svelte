<script>
    import Header from "$lib/components/Header.svelte";
    import Sidebar from "$lib/components/Sidebar.svelte";
    //   import ManageUser from './routes/ManageUser.svelte';
    //   impot ManageComplaint from './routes/ManageComplaint.svelte';
    //   import ManageAnnouncement from './routes/ManageAnnouncement.svelte';
    //   import SystemConfiguration from './routes/SystemConfiguration.svelte';
    //   import Logout from './routes/Logout.svelte';

    let adminName = "Admin Name"; // Replace with the actual admin name
</script>

<div class="container">
    <Sidebar />
    <div class="header">
        <Header {adminName} />
    </div>
    <div class="content">
        <slot />
    </div>
</div>

<style>
    /* Add your main container styling here */

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
    }
    .header {
        top: 0;
        left: 0;
        right: 0;
        display: block;
        width: 100vw;
        position: absolute;
    }
</style>
