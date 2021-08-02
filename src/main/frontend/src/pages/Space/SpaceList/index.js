import Utils from '../../../utils';
import React, { useState, useEffect } from 'react';
import { List} from 'semantic-ui-react';
function SpaceList(props) {

    const [userList, setUserList] = useState([]);

    useEffect( () => {
        async function fetchData(){
            let response = await Utils.getData("/api/user/list");
            if (200 === response.status) {
                let responseBody = await response.json();
                setUserList(responseBody.result);
            }
        }
        fetchData();
    }, [])

    function renderList() {
        let result = [];
        for (let user of userList) {
            result.push(<List.Item as='a' 
                onClick={() => props.history.push('/space/view/'+user.id)}>
                <List.Icon name='user' />
                <List.Content>
                    <List.Header>Space#{user.id}</List.Header>
                    <List.Description>{user.name}</List.Description>
                </List.Content>
            </List.Item>)
        }
        return result;
    }

    return (<div className='whole_page'>
        <h2>this is the space list</h2>
        <div className='middle_block'>
            <List size='huge' divided>{renderList()}</List>
        </div>
    </div>);
}

export default SpaceList;