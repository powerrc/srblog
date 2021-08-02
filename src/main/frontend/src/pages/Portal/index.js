import React from 'react';
import { withRouter, Redirect } from 'react-router';
import { Grid, Button, Icon, Table,Modal } from 'semantic-ui-react';
import Utils from '../../utils';

import AddPost from './AddPost';
import ChangeTheme from './ChangeTheme';
import '../../scss/theme.scss';

class Portal extends React.PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            backToLogin:false,
            showAdd:false,
            showTheme:false,
            userId:props.match.params.id,
            user: {},
            posts: []
        }
    }

    componentDidMount=async()=>{
        let user = await Utils.getTokenUser()
        if(null===user){
            this.setState({backToLogin:true})
        }else{
            this.loadData();
        }
        
    }

    loadData = async () => {
        let userResponse = await Utils.getData('/api/user?id=' + this.state.userId);
        let postResponse = await Utils.getData('/api/post/list?userId=' + this.state.userId);
        if (200 === userResponse.status && 200 === postResponse.status) {
            let userReponseBody = await userResponse.json();
            let postReponseBody = await postResponse.json();
            this.setState({
                user: userReponseBody.result,
                posts: postReponseBody.result
            });
            return;
        }
    }

    addCallBack=async()=>{
        await this.loadData();
        this.toggleAddModal();
    }

    themeCallBack=async()=>{
        await this.loadData();
        this.toggleThemeModal();
    }

    toggleAddModal=()=>{
        let oldState = this.state.showAdd;
        this.setState({showAdd:!oldState});
    }

    toggleThemeModal=()=>{
        let oldState = this.state.showTheme;
        this.setState({showTheme:!oldState});
    }


    logout=()=>{
        Utils.saveToken('');
        Utils.getData('/api/user/logout',true);
        this.props.history.push('/space/view/'+this.state.userId);
    }

    getAddPostModal=()=>{
        return (<Modal
            closeIcon
            dimmer='blurring'
            onClose={this.toggleAddModal}
            centered={false}
            open={this.state.showAdd}
          >
            <Modal.Content scrolling>
             <AddPost addCallBack={this.addCallBack}/>
            </Modal.Content>
           
          </Modal>);
    }

    getThemeModal=()=>{
        return (<Modal
            size='mini'
            closeIcon
            dimmer='blurring'
            onClose={this.toggleThemeModal}
            centered={false}
            open={this.state.showTheme}
          >
            <Modal.Content scrolling>
             <ChangeTheme theme={this.state.user.theme} themeCallBack={this.themeCallBack}/>
            </Modal.Content>
           
          </Modal>);
    }

    deletePost=async(postId)=>{
        let response = await Utils.deleteRequest('/api/post',{id:postId});
        if(200===response.status){
           return this.loadData();
        }
    }

    getPostTable = () => {

        let postRows = [];
        for (let post of this.state.posts) {
            postRows.push(<Table.Row key={post.id}>
                <Table.Cell>{post.id}</Table.Cell>
                <Table.Cell>{post.title}</Table.Cell>
                <Table.Cell>{post.description}</Table.Cell>
                <Table.Cell textAlign='right'><Button labelPosition='left' onClick={()=>this.deletePost(post.id)} icon negative><Icon name='remove' />Delete</Button></Table.Cell>
            </Table.Row>)

        }
        if (postRows.length < 1) {
            postRows.push(<Table.Row key="0" textAlign='center'><Table.Cell colSpan={4}>No post</Table.Cell></Table.Row>)
        }

        return <>
            <Table compact striped className='post_table'>
                <Table.Header>
                    <Table.Row>
                        <Table.HeaderCell>#</Table.HeaderCell>
                        <Table.HeaderCell>Title</Table.HeaderCell>
                        <Table.HeaderCell>Description</Table.HeaderCell>
                        <Table.HeaderCell textAlign='right'>Action</Table.HeaderCell>
                    </Table.Row>

                </Table.Header>
                <Table.Body>{postRows}</Table.Body>
                <Table.Footer fullWidth>
                    <Table.Row>
                        <Table.HeaderCell colSpan='4'>
                            <Button
                                onClick={this.toggleAddModal}
                                floated='right'
                                icon
                                labelPosition='left'
                                primary
                                size='small'
                            >
                                <Icon name='plus' /> Add Post
                            </Button>
                        </Table.HeaderCell></Table.Row>
                </Table.Footer>
            </Table></>
    }

    render() {

        if (this.state.backToLogin) {
            return <Redirect to='/login' />
        }

        return <Grid className='page_grid'>
            {this.getAddPostModal()}
            {this.getThemeModal()}
            <Grid.Row centered className='title'><span>You are viewing the admin portal of user:{this.state.user.name}</span></Grid.Row>
            <Grid.Row centered className={this.state.user.theme+'-theme'} ><span>Current theme:{this.state.user.theme}</span></Grid.Row>
            <Grid.Row centered className='buttons_row'>
                <Button labelPosition='left' icon onClick={()=>this.props.history.push('/space/view/'+this.state.user.id)}><Icon name='play'/>Go To My Space</Button>
                <Button labelPosition='left' icon positive onClick={this.toggleThemeModal}><Icon name='refresh'/>Change Theme</Button>
                <Button labelPosition='left' icon onClick={this.logout}><Icon name='sign-out'/>Logout</Button>
            </Grid.Row>
          
            <Grid.Row className='content'>
                {this.getPostTable()}
            </Grid.Row>
        </Grid>
    }
}

export default withRouter(Portal);