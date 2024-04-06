<script>
    import Cookies from "js-cookie";

    // Function to handle logout
    function logout() {
        const a = Cookies.get("StudentPortalAuthToken");
        if (a) {
            Cookies.remove("StudentPortalAuthToken");
            window.location.href = "/";
        } else {
            alert("You are already logged out!");
            // Optionally, redirect the user to another page
            // window.location.href = "/"; // Redirect to home page
        }
    }
</script>

{#if !Cookies.get("StudentPortalAuthToken")}
    <h1>You are already logged out!</h1>
    <button type="submit"><a href="/login">Login</a></button>
{:else}
    <h1>Are you sure you want to LOGOUT?</h1>
    <button on:click={logout} type="submit">Logout</button>
{/if}
