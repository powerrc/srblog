import React from 'react';
import { Route, Redirect,Switch} from "react-router-dom";
import SpaceList from './SpaceList';
import View from './View';

function Space(props){

        return (<>
        <Switch>
             <Route path='/space/list'><SpaceList history={props.history}/></Route>
             <Route path='/space/view/:id?'><View history={props.history}/></Route>
             <Redirect exact from='/space' to='/space/list' />
        </Switch>
          </>
        );
    
}

export default Space;