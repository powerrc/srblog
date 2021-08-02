import React, { useState } from 'react';
import { Form, Button, Divider, Input, Message } from 'semantic-ui-react';
import { useForm, Controller } from "react-hook-form";
import Utils from '../../utils';


function Signup(props) {

  const { control, handleSubmit, clearErrors, formState: { errors } } = useForm();

  const [errorMsg, setErrorMsg] = useState('');

  const [formLoading, setFormLoading] = useState(false);

  function getInputError(fieldName) {
    if (errors.hasOwnProperty(fieldName)) {
      return { content: "Please enter a valid input." }
    }
  }

  const onSubmit = async (data) => {
    setFormLoading(true);
    if (Utils.hash(data.password1) !== Utils.hash(data.password2)) {
      setErrorMsg("Password doesn't match!");
      setFormLoading(false);
      return;
    }

    let response = await Utils.postForm('/api/user', { name: data.name, password: data.password1 });
    let reponseBody = await response.json();

    if (200 !== response.status) {
      setErrorMsg(reponseBody.message);
      setFormLoading(false);
      return;
    }

    props.history.push({pathname:'/login',state:{success:"Thanks for you signup , now you can login."}});
  }

  return (
    <div className='whole_page'>

      <Form loading={formLoading} error={'' !== errorMsg} className='middle_block' onSubmit={handleSubmit(onSubmit)}>
        <Form.Field><h2>Sign up</h2></Form.Field>
        <Divider />
        <Controller
          name='name'
          control={control}
          rules={{ required: true }}
          render={({ field }) => <Form.Field error={getInputError('name')} control={Input} label='Name' {...field} />}
        />
        <Controller
          name='password1'
          control={control}
          rules={{ required: true }}
          render={({ field }) => <Form.Field error={getInputError('password1')} control={Input} type='password' label='Enter Password' {...field} />}
        />
        <Controller
          name='password2'
          control={control}
          rules={{ required: true }}
          render={({ field }) => <Form.Field error={getInputError('password2')} control={Input} type='password' label='Enter Password Again' {...field} />}
        />
        <Message error header={errorMsg} />
        <Button type='submit' onClick={() => clearErrors()} >Submit</Button>
      </Form>
    </div>
  );
}

export default Signup;