<script>
    /**
     * @type {any}
     */
    export let adminName;
    let showProfileDropdown = false;

    function toggleProfileDropdown() {
        showProfileDropdown = !showProfileDropdown;
    }
</script>

<div class="header">
    <div class="header-title">
        <h1>Complaint Management System</h1>
    </div>
    <!-- svelte-ignore a11y-click-events-have-key-events -->
    <!-- svelte-ignore a11y-no-static-element-interactions -->
    <div class="admin-profile" on:click={toggleProfileDropdown}>
        <img src="/assets/admin-logo.png" alt="Admin Logo" />
        <div class="admin-info">
            <p>{adminName}</p>
            <div class="profile-dropdown">
                <span>&#9662;</span>
                {#if showProfileDropdown}
                    <div class="dropdown-content">
                        <p>Email: admin@example.com</p>
                        <p>Role: Administrator</p>
                    </div>
                {/if}
            </div>
        </div>
    </div>
</div>

<style>
    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 20px 0px;
        background-color: #3498db;
        color: #ffffff;
        margin: 0;
        position: absolute;
        top: 0;
        width: 100%;
    }

    .header h1 {
        margin: 0;
        padding: 0px 20px;
    }

    .admin-profile {
        display: flex;
        align-items: center;
        padding: 0px 20px;
    }

    .admin-profile img {
        width: 30px;
        height: 30px;
        border-radius: 50%;
        margin-right: 10px;
    }

    .profile-dropdown {
        position: relative;
        display: inline-block;
        cursor: pointer;
    }

    .profile-dropdown span {
        font-size: 18px;
        margin-left: 5px;
    }

    .dropdown-content {
        display: block;
        position: absolute;
        background-color: #f9f9f9;
        box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
        z-index: 1;
        padding: 10px;
        top: 30px;
        right: 0;
        color: black;
    }

    .dropdown-content:hidden {
        display: none;
    }

    .admin-info {
        display: flex;
        flex-direction: row;
        align-items: center;
    }
</style>
