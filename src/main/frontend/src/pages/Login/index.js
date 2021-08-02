import React, { useState } from 'react';
import { useLocation } from "react-router-dom";
import { Form, Button, Divider ,Input,Message} from 'semantic-ui-react';
import { useForm, Controller } from "react-hook-form";
import  Utils  from '../../utils';

function Login(props){

    const location = useLocation();

    const { control, handleSubmit, clearErrors,formState: { errors } } = useForm();

    const [errorMsg, setErrorMsg] = useState('');

    const [formLoading, setFormLoading] = useState(false);

  function getInputError(fieldName) {
    if (errors.hasOwnProperty(fieldName)) {
      return { content: "Please enter a valid input." }
    }
  }

  function getSuccess(){
    return location.state?location.state.success:'';
  }

  const onSubmit = async (data) => {
    
    if(location.state){
        location.state.success='';
    }
    
    setFormLoading(true);
    let response = await Utils.postForm('/api/user/login', { name: data.name, password: data.password });
    let reponseBody = await response.json();

    if (200 !== response.status) {
      setErrorMsg(reponseBody.message);
      setFormLoading(false);
      return;
    }
    Utils.saveToken(reponseBody.result.tokenString);
    if(location.state &&true=== location.state.goToSpace){
      return props.history.push('/space/view/'+reponseBody.result.userId);
    }else{
      return props.history.push('/portal/'+reponseBody.result.userId);
    }

  }

    return(<div className='whole_page'>

    <Form loading={formLoading} error={'' !== errorMsg} success={''!==getSuccess()} className='middle_block' onSubmit={handleSubmit(onSubmit)}>
      <Form.Field><h2>Login</h2></Form.Field>
      <Divider />
      <Message success header={getSuccess()} />
      <Controller
        name='name'
        control={control}
        rules={{ required: true }}
        render={({ field }) => <Form.Field error={getInputError('name')} control={Input} label='Name' {...field} />}
      />
      <Controller
        name='password'
        control={control}
        rules={{ required: true }}
        render={({ field }) => <Form.Field error={getInputError('password')} control={Input} type='password' label='Enter Password' {...field} />}
      />
      <Message error header={errorMsg} />
      <Button type='submit' onClick={() => clearErrors()} >Submit</Button>
    </Form>
  </div>);
}

export default Login;