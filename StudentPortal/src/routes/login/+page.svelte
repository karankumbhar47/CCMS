<script>
    import Cookies from "js-cookie";
    import { getDefaultApi } from "$lib/utils/auth";
    import { goto } from "$app/navigation";
    import { Role } from "$lib/generated";

    let username = "";
    let password = "";

    // const api = new DefaultApi();

    async function handleLogin() {
        try {
            let res = await getDefaultApi().login({
                loginRequest: {
                    username: username,
                    password: password,
                    role: Role.User,
                },
            });
            setCookie("StudentPortalAuthToken", res);
            goto("/");
        } catch (e) {
            console.log("Invalid username or password");
            console.log(e);
        }
    }

    /**
     * Set a cookie with the provided key and value.
     * @param {string} key - The key for the cookie.
     * @param {string} value - The value to set for the cookie.
     */
    function setCookie(key, value) {
        Cookies.set(key, value, { expires: 1 });
    }
</script>

<h1>LOGIN PAGE</h1>

<form on:submit|preventDefault={handleLogin}>
    <input type="text" placeholder="Username" bind:value={username} />
    <input type="password" placeholder="Password" bind:value={password} />
    <button type="submit">Login</button>
</form>

<style>
    h1 {
        text-align: center;
        margin-bottom: 20px;
    }

    form {
        max-width: 400px;
        margin: 0 auto;
        padding: 20px;
        border: 1px solid #ccc;
        border-radius: 5px;
        background-color: #f9f9f9;
    }

    input {
        width: 100%;
        margin-bottom: 10px;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    button {
        width: 100%;
        padding: 10px;
        border: none;
        border-radius: 5px;
        background-color: #007bff;
        color: #fff;
        cursor: pointer;
    }

    button:hover {
        background-color: #0056b3;
    }
</style>
