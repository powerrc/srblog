import React, { useState } from 'react';
import { Form, Button, Divider ,Input,Message,TextArea} from 'semantic-ui-react';
import { useForm, Controller } from "react-hook-form";
import  Utils  from '../../../utils';


function AddPost(props){
    const { control, handleSubmit, clearErrors,formState: { errors } } = useForm();

    const [errorMsg, setErrorMsg] = useState('');

    const [formLoading, setFormLoading] = useState(false);


    function getInputError(fieldName) {
        if (errors.hasOwnProperty(fieldName)) {
          return { content: "Please enter a valid input." }
        }
      }
    

    const onSubmit=async(data)=>{
        setFormLoading(true);
        let response = await Utils.postForm('/api/post', { ...data },true);
        let reponseBody = await response.json();
    
        if (200 !== response.status) {
          setErrorMsg(reponseBody.message);
          setFormLoading(false);
          return;
        }
       props.addCallBack();
    }

    return(
<Form loading={formLoading} error={'' !== errorMsg}  className='middle_block' onSubmit={handleSubmit(onSubmit)}>
      <Form.Field><h2>Add Post</h2></Form.Field>
      <Divider />
      <Controller
        name='title'
        control={control}
        rules={{ required: true }}
        render={({ field }) => <Form.Field required error={getInputError('title')} control={Input} label='Title' {...field} />}
      />
      <Controller
        name='description'
        control={control}
        rules={{ required: true }}
        render={({ field }) => <Form.Field required error={getInputError('description')} control={Input} label='Description' {...field} />}
      />
      <Controller
        name='content'
        control={control}
        render={({ field }) => <Form.Field control={TextArea} label='Content' {...field} />}
      />
      
      <Message error header={errorMsg} />
      <Button type='submit' onClick={() => clearErrors()} >Submit</Button>
    </Form>
    );
}

export default AddPost;