<script>
    /** @type {string} */
    export let userName;
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
    <div class="user-profile" on:click={toggleProfileDropdown}>
        <img src="/assets/user-logo.png" alt="User Logo" />
        <div class="user-info">
            <p>{userName}</p>
            <div class="profile-dropdown">
                <span>&#9662;</span>
                {#if showProfileDropdown}
                    <div class="dropdown-content">
                        <p>Email: student@example.com</p>
                        <p>Role: Student</p>
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
        padding: 10px 20px;
        background-color: #3498db;
        color: #ffffff;
        margin: 0;
        position: absolute;
        top: 0;
        width: 100%;
    }

    .header h1 {
        margin: 0;
    }

    .user-profile {
        display: flex;
        align-items: center;
    }

    .user-profile img {
        width: 30px;
        height: 30px;
        border-radius: 50%;
        margin-right: 10px;
    }

    .user-info {
        display: flex;
        flex-direction: row;
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
</style>
