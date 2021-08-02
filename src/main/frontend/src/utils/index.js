import md5 from 'md5';
const createReactClass = require('create-react-class');


const URL_PREFIX='';

const Utils =  createReactClass({
    statics: {
        saveToken:(token)=>{
            localStorage.setItem('token',token);
        },
        getToken:()=>{
            return "string"===typeof(localStorage.getItem('token'))?localStorage.getItem('token'):'';
        },
        getTokenUser:async()=>{
            if("string"!==typeof(localStorage.getItem('token')))
            {
                return null;
            }

            let requestObject = {
                method: 'GET',
                cache: 'default',
                headers:new Headers({tokenString:localStorage.getItem('token')})
            };

            let response =  await fetch(URL_PREFIX+'/api/user/islogin',requestObject );
            if( 200===response.status){
                let responseBody = await response.json();
                return responseBody.result.user;
            }
            return null;
        },
        hash:(input)=>{
            return md5(input);
        },
        getData:async(url,withToken=false)=>{
            let requestObject = {
                method: 'GET',
                cache: 'default',
            };
            if(withToken){
                requestObject['headers']=new Headers({tokenString:localStorage.getItem('token')});
            }

            return await fetch(URL_PREFIX+url,requestObject );
        },
        deleteRequest:async(url,request)=>{
            let bodyFormData = new FormData();
            for (let key in request) {
                bodyFormData.append(key, request[key]);
            }
            let requestObject = {
                method: 'DELETE',
                cache: 'default',
                body:bodyFormData,
                headers:new Headers({tokenString:localStorage.getItem('token')})
            };
            
            return await fetch(URL_PREFIX+url,requestObject );
        },
        putForm:async(url,request)=>{
            let bodyFormData = new FormData();
            for (let key in request) {
                bodyFormData.append(key, request[key]);
            }

            let requestObject = {
                method: 'PUT',
                body:bodyFormData,
                cache: 'default',
                headers:new Headers({tokenString:localStorage.getItem('token')})
            };

            return await fetch(URL_PREFIX+url,requestObject );
        },
        postForm:async(url,request,withToken=false)=>{
            let bodyFormData = new FormData();
            for (let key in request) {
                bodyFormData.append(key, request[key]);
            }

            let requestObject = {
                method: 'POST',
                body:bodyFormData,
                cache: 'default'
            };
            if(withToken){
                requestObject['headers']=new Headers({tokenString:localStorage.getItem('token')});
            }

            return await fetch(URL_PREFIX+url,requestObject );
        }
    },
    render() {
        return;
    }
  });
export default Utils;