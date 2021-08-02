import React,{useState} from "react";
import { Form,Message } from "semantic-ui-react";
import  Utils  from '../../../utils';

function ChangeTheme(props){

    const[themeValue,setThemeValue]=useState(props.theme)
    const [formLoading, setFormLoading] = useState(false);
    const [errorMsg, setErrorMsg] = useState('');

    async function changeTheme(){

        if(props.theme!==themeValue){
            setFormLoading(true);
            let response = await Utils.putForm('/api/user/theme', {theme:themeValue},true);
            let reponseBody = await response.json();
        
            if (200 !== response.status) {
              setErrorMsg(reponseBody.message);
              setFormLoading(false);
              return;
            }
        }
       props.themeCallBack();
    }

    return(
        <Form size='huge' loading={formLoading} error={'' !== errorMsg}>
            <Form.Field>
                <Form.Radio 
                    name='theme' 
                    value='default' 
                    className='default-theme' 
                    label='Grey (default)' 
                    checked={themeValue==='default'} 
                    onChange={(e,{value})=>setThemeValue(value)}/>
                <Form.Radio 
                    name='theme' 
                    value='blue' 
                    className='blue-theme' 
                    label='Blue'  
                    checked={themeValue==='blue'}
                    onChange={(e,{value})=>setThemeValue(value)}/>
                <Form.Radio 
                    name='theme'
                    value='green' 
                    className='green-theme' 
                    label='Green'  
                    checked={themeValue==='green'}
                    onChange={(e,{value})=>setThemeValue(value)}/>
            </Form.Field>
            <Form.Field>
              <Message error header={errorMsg} />  
              <Form.Button primary onClick={changeTheme}>Submit</Form.Button>
            </Form.Field>
        </Form>
    );
}

export default ChangeTheme;