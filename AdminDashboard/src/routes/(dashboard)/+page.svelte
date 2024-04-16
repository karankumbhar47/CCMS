<!-- Overview.svelte -->

<script>
    import LayoutGrid, { Cell } from "@smui/layout-grid";
    import Paper, { Title, Subtitle, Content } from "@smui/paper";
    import IconButton from "@smui/icon-button";

    export let userCount = 50;
    export let totalComplaints = 10;
    export let pendingComplaints = 2;
    export let resolverCount = 2;

    export let clicked = 0;

    // import Cookies from 'js-cookie';

    // const authToken = Cookies.get("authToken");
    // if (!authToken && window.location.pathname !== "/login") {
    //     window.location.href = '/login';
    // }
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
    <Cell>
        <Paper>
            <Title>No. of Resolvers</Title>
            <Subtitle><h2>{resolverCount}</h2></Subtitle>
            <Content>
                <IconButton class="material-icons" on:click={() => clicked++}>
                    show_chart
                </IconButton>
            </Content>
        </Paper>
    </Cell>
</LayoutGrid>
