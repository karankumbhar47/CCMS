<!-- Overview.svelte -->

<script>
    export let userCount = 50;
    export let totalComplaints = 10;
    export let pendingComplaints = 2;
    export let resolverCount = 2;
</script>

<div class="overview-content">
    <div class="overview-grid">
        <div class="overview-box">
            <h2>No. of Registered Users</h2>
            <p>{userCount}</p>
        </div>
        <div class="overview-box">
            <h2>Total Complaints</h2>
            <p>{totalComplaints}</p>
        </div>
        <div class="overview-box">
            <h2>Pending Complaints</h2>
            <p>{pendingComplaints}</p>
        </div>
        <div class="overview-box">
            <h2>No. of Resolvers</h2>
            <p>{resolverCount}</p>
        </div>
    </div>
</div>

<style>
    /* Add your styling for the overview content and boxes here */
    .overview-content {
        flex: 1;
        padding: 20px;
    }

    .overview-grid {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        grid-gap: 20px;
    }

    .overview-box {
        border: 1px solid #ddd;
        padding: 20px;
        text-align: center;
    }
</style>
