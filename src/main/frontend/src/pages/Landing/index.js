import React from 'react';
import {List,Icon} from 'semantic-ui-react'


const Landing=(props)=>(

      <div className='whole_page'><h1>Welcome to the srblog</h1>
      <h4>this is the landing page , please click the link below:</h4>
      <div  className='middle_block'>
      <List  size='huge' divided>
        <List.Item as='a' onClick={()=>props.history.push('/signup')}>
              <Icon name='signup' color='blue'/>
              <List.Content>
                <List.Header>Sign up a new account</List.Header>
              </List.Content>
        </List.Item>
        <List.Item as='a' onClick={()=>props.history.push('/login')}>
              <Icon name='sign-in' color='green'/>
              <List.Content>
                <List.Header>Login</List.Header>
              </List.Content>
        </List.Item>
        <List.Item as='a' onClick={()=>props.history.push('/space/list')}>
              <Icon name='eye'/>
              <List.Content>
                <List.Header>View spaces list</List.Header>
            </List.Content>
        </List.Item>
       </List>
      </div>
       
      </div>
    
);

export default Landing;