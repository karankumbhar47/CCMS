<!-- Overview.svelte -->

<script>
    import LayoutGrid, { Cell } from "@smui/layout-grid";
    import Paper, { Title, Subtitle, Content } from "@smui/paper";
    import IconButton from "@smui/icon-button";
    import { onMount } from "svelte";
    import { getDefaultApi } from "$lib/utils/auth";

    let userCount = 0;
    let totalComplaints = 0;
    let pendingComplaints = 0;

    let clicked = 0;

    // import Cookies from 'js-cookie';

    // const authToken = Cookies.get("authToken");
    // if (!authToken && window.location.pathname !== "/login") {
    //     window.location.href = '/login';
    // }
    onMount(async () => {
        await loadStats();
    });

    async function loadStats() {
        try {
            const response = await getDefaultApi().stats();
            userCount = response.users;
            totalComplaints = response.complaints;
            pendingComplaints = response.pendingComplaints;
        } catch (error) {
            console.error("Failed to fetch user details:", error);
        }
    }
</script>

<LayoutGrid>
    <Cell>
        <Paper>
            <Title>No. of Registered Users</Title>
            <Subtitle><h2>{userCount}</h2></Subtitle>
            <Content>
                <IconButton class="material-icons" on:click={() => clicked++}>
                    show_chart
                </IconButton>
            </Content>
        </Paper>
    </Cell>
    <Cell>
        <Paper>
            <Title>Total Complaints</Title>
            <Subtitle><h2>{totalComplaints}</h2></Subtitle>
            <Content>
                <IconButton class="material-icons" on:click={() => clicked++}>
                    show_chart
                </IconButton>
            </Content>
        </Paper>
    </Cell>
    <Cell>
        <Paper>
            <Title>Pending Complaints</Title>
            <Subtitle><h2>{pendingComplaints}</h2></Subtitle>
            <Content>
                <IconButton class="material-icons" on:click={() => clicked++}>
                    show_chart
                </IconButton>
            </Content>
        </Paper>
    </Cell>
    <!-- <Cell>
        <Paper>
            <Title>No. of Resolvers</Title>
            <Subtitle><h2>{resolverCount}</h2></Subtitle>
            <Content>
                <IconButton class="material-icons" on:click={() => clicked++}>
                    show_chart
                </IconButton>
            </Content>
        </Paper>
    </Cell> -->
</LayoutGrid>
