<script>
    import Cookies from "js-cookie";
    import { DefaultApi } from "$lib/generated";

    let username = "";
    let password = "";

    const api = new DefaultApi();

    async function handleLogin() {
        try {
            let res = await api.login({
                loginRequest: {
                    username: username,
                    password: password,
                },
            });
            setCookie("authToken", res);
        } catch (e) {
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
    <input type="text" placeholder="Password" bind:value={password} />
    <button type="submit">Login</button>
</form>
