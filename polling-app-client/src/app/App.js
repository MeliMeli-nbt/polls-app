import React, { Component } from "react";
import "./App.css";
import { Route, withRouter, Switch } from "react-router-dom";

import Login from "../user/login/Login";
import Signup from "../user/signup/Signup";
import AppHeader from "../common/AppHeader";
import NotFound from "../common/NotFound";

import { Layout } from "antd";
const { Content } = Layout;

class App extends Component {

  render() {
    return (
      <Layout className="app-container">
        <AppHeader />

        <Content className="app-content">
          <div className="container">
            <Switch>
              <Route path="/login" render={Login}></Route>
              <Route path="/signup" component={Signup}></Route>
              <Route component={NotFound}></Route>
            </Switch>
          </div>
        </Content>
      </Layout>
    );
  }
}

export default withRouter(App);
