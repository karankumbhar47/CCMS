<script>
    import { goto } from "$app/navigation";
    import Cookies from "js-cookie";

    // Function to handle logout
    function logout() {
        const token = Cookies.get("StudentPortalAuthToken");
        if (token !== undefined) {
            Cookies.remove("StudentPortalAuthToken");
            goto("/login");
        } else {
            goto("/login");
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
