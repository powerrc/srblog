import React from 'react';
import { Switch, Redirect, Route, HashRouter } from "react-router-dom";
import { createBrowserHistory } from "history";
import LoadingSpin from './components/LoadingSpin';

import './scss/App.scss';
import 'semantic-ui-css/semantic.min.css'

const Landing = React.lazy(() => import('./pages/Landing'));
const Signup = React.lazy(() => import('./pages/Signup'));
const Login = React.lazy(() => import('./pages/Login'));
const Space = React.lazy(() => import('./pages/Space'));
const Portal = React.lazy(() => import('./pages/Portal'));

const history = createBrowserHistory();

function App() {
  return (
    <div className="App">
      <HashRouter>
        <React.Suspense fallback={<LoadingSpin />}>
          <Switch>
            <Route path='/space' component={Space} history={history} />
            <Route path='/portal/:id' component={Portal} history={history} />
            <Route path='/signup' component={Signup} history={history} />
            <Route path='/login' component={Login} history={history} />
            <Route path='/landing' component={Landing} history={history} />
            <Redirect exact from='/' to='/landing' />
            <Route render={() => <Redirect to="/" />} />
          </Switch>
        </React.Suspense>
      </HashRouter>
    </div>
  );
}

export default App;
