import React from 'react';
import { List,Icon,Button,Modal,Form } from 'semantic-ui-react';
import Utils from '../../utils';

class Comments extends React.PureComponent {

    constructor(props){
        super(props);
        this.state={
            postId:props.postId,
            showAdd:false,
            newComment:'',
            comments:[]
        }
    }

    componentDidMount(){
       this.loadComments();
    }

    loadComments=async()=>{
        let response = await Utils.getData('/api/comment/list?postId='+this.state.postId);
        if(200===response.status){
            let respoonseBody = await response.json();
            this.setState({comments:respoonseBody.result,showAdd:false});
        }
    }

    renderCommentList() {
        let result = [];

        for(let comment of this.state.comments){
            result.push(<List.Item key={comment.id}>{comment.content}</List.Item>); 
        }

        if (0 === result.length) {
            return <List.Item>There is no comment yet.</List.Item>
        }

        return result;
    }

    toggleModal=()=>{
        let oldState = this.state.showAdd;
        this.setState({showAdd:!oldState});
    }

    addComment=async()=>{
        let request={
            postId:this.state.postId,
            content:this.state.newComment
        }
        let response = await Utils.postForm('/api/comment',request,true);
        if(200===response.status){
            return this.loadComments();
        }
    }

    getModalButtons(){
        if(Utils.getToken().length<1){
         return <Modal.Actions>
          <p>To add a comment , you have to login first.</p>
          <Button 
          onClick={()=>this.props.history.push({pathname:'/login',state:{goToSpace:true}})}>Go to login</Button>
         </Modal.Actions>
        }else{
            return  <Modal.Actions>
            <Button onClick={this.toggleModal}>Cancel</Button>
            <Button onClick={this.addComment} positive>OK</Button>
          </Modal.Actions>;
        }
    }

    getModal(){
      return (<Modal
        dimmer='blurring'
        size='mini'
        onClose={this.toggleModal}
        centered={false}
        open={this.state.showAdd}
      >
        <Modal.Header>Add comment for post#{this.state.postId}</Modal.Header>
        <Modal.Content>
         <Form>
             <Form.TextArea 
                className='comment_textarea' 
                onChange={(e,{value})=>this.setState({newComment:value})}/>
         </Form>
        </Modal.Content>{this.getModalButtons()}
       
      </Modal>);
    }


    render() {
        return (
            <>
            {this.getModal()}
            <List>
                <List.Item>
                    <Icon name='comment'/>
                    <List.Content>Comments:<List bulleted>{this.renderCommentList()}</List></List.Content>
               </List.Item>
               <List.Item><Button size='mini' compact icon primary labelPosition='left' onClick={this.toggleModal}><Icon name='plus'/>Add comment</Button></List.Item>
            </List>
            </>
        );
    }
}

export default Comments;