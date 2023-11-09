import React, { Component } from 'react';
import {
    Link,
    withRouter
} from 'react-router-dom';
import './AppHeader.css';
import { Layout, Menu,  } from 'antd';
const Header = Layout.Header;
    
class AppHeader extends Component {

    render() {
        const menuItems = [
          <Menu.Item key="/login">
            <Link to="/login">Login</Link>
          </Menu.Item>,
          <Menu.Item key="/signup">
            <Link to="/signup">Signup</Link>
          </Menu.Item>                  
        ];

        return (
            <Header className="app-header">
            <div className="container">
              <div className="app-title" >
                <Link to="/">Polling App</Link>
              </div>
              <Menu
                className="app-menu"
                mode="horizontal"
                selectedKeys={[this.props.location.pathname]}
                style={{ lineHeight: '64px' }} >
                  {menuItems}
              </Menu>
            </div>
          </Header>
        );
    }
}

export default withRouter(AppHeader);