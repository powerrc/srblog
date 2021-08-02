import React from 'react';
import {withRouter } from 'react-router';
import { Grid ,List,Button} from 'semantic-ui-react';
import Comments from '../../../components/Comments';
import Utils from '../../../utils';

import '../../../scss/theme.scss';

class View extends React.Component{
    
    constructor(props){
        super(props);
        this.state={
            backToList:false,
            user:{},
            posts:[],
            tokenUser:null,
            userId:props.match.params.id
        };
    }

    componentDidMount=()=>{
       this.loadData();
    }

    loadData=async()=>{
        let userResponse = await Utils.getData('/api/user?id='+this.state.userId);
        let postResponse = await Utils.getData('/api/post/list?userId='+this.state.userId);
        if(200===userResponse.status&&200===postResponse.status){
            let userReponseBody = await userResponse.json();
            let postReponseBody = await postResponse.json();
            this.setState({
                user:userReponseBody.result,
                posts:postReponseBody.result
             });
        }

        let user = await Utils.getTokenUser();
        if(null!==user){
            this.setState({tokenUser:user});
        }
     
     }

    
    getPostList=()=>{
      let result = [];
      for(let post of this.state.posts){
          result.push(
            <List.Item key={post.id}>
                <List.Icon name='paperclip'/>
                <List.Content>
                 <List.Header className='post_title'><span>{post.title}</span></List.Header>
                 <List.Description>{post.description}</List.Description>
                </List.Content>
                <List.Content className='post_content'>{post.content}</List.Content>
                <List.Content className='comment_list'><Comments postId={post.id} history={this.props.history}/></List.Content>
            </List.Item>
          )
      }

      if(0===result.length){
          return <List.Item>This user hasn't posted anything yet.</List.Item>
      }
      return result;
    }

    getButtonsRow=()=>{
       
        if(null===this.state.tokenUser){
            return <><span>You are a vistor.</span>
                    <Button onClick={()=>this.props.history.push({pathname:'/login',state:{goToSpace:true}})}>Go to login</Button>
                    <Button onClick={()=>this.props.history.push('/space/list')}>Go to space list</Button>
                    </>
        }else{
            return <><span>You are logged in as :{this.state.tokenUser.name}.</span>
                    <Button onClick={()=>this.props.history.push('/portal/'+this.state.tokenUser.id)}>Go to my portal</Button>
                    <Button onClick={()=>this.props.history.push('/space/list')}>Go to space list</Button>
                    </>
        }
        
    }

    render(){

        if(this.state.user.hasOwnProperty('name')){

        let pageClass=['page_grid',this.state.user.theme+'-theme'];

         return <> <Grid className={pageClass.join(' ')}>
                <Grid.Row centered className='title'><span>You are viewing blog space of user:{this.state.user.name}</span></Grid.Row>
                <Grid.Row centered className='buttons_row'>{this.getButtonsRow()}</Grid.Row>
                <Grid.Row className='content'>
                    <List divided size='huge' className='post_list'>{this.getPostList()}</List>
                </Grid.Row>
            </Grid>
        </>
        }
        return <></>;
    }
}
export default withRouter(View);