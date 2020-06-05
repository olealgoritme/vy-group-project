import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { withRouter } from "react-router";
import ProfileMenu from './Menus/profileMenu';
import { connect } from 'react-redux';
import { toggleSidebar  } from '../../store/actions';

// import { getLoggedInUser } from '../../helpers/authUtils';

import logoLight from "../../images/logo-light.png";
import logoDark from "../../images/logo-dark.png";
import logoSmall from "../../images/logo-sm.png";

class Topbar extends Component {

    constructor(props) {
        super(props);
        this.state = {
            create_menu: false,
            istoggle : false
        };
        this.toggleCreate = this.toggleCreate.bind(this);
    }
    toggleCreate() {
        this.setState(prevState => ({
            create_menu: !prevState.create_menu
        }));
    }

    sidebarToggle = () =>{
        document.body.classList.toggle('enlarged');
        this.props.toggleSidebar(!this.props.is_toggle);
     }

    
    toggleFullscreen() {
        if (!document.fullscreenElement && /* alternative standard method */ !document.mozFullScreenElement && !document.webkitFullscreenElement) {  // current working methods
            if (document.documentElement.requestFullscreen) {
                document.documentElement.requestFullscreen();
            } else if (document.documentElement.mozRequestFullScreen) {
                document.documentElement.mozRequestFullScreen();
            } else if (document.documentElement.webkitRequestFullscreen) {
                document.documentElement.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT);
            }
        } else {
            if (document.cancelFullScreen) {
                document.cancelFullScreen();
            } else if (document.mozCancelFullScreen) {
                document.mozCancelFullScreen();
            } else if (document.webkitCancelFullScreen) {
                document.webkitCancelFullScreen();
            }
        }
    }

    render() {
        return (
            <React.Fragment>
                <div className="topbar">
                    <div className="topbar-left">
                        <Link to="/" className="logo">
                            <span className="logo-light">
                                {this.props.is_light ?  <img src={logoLight} alt="" height="90" /> :  <img src={logoDark} alt="" height="90" /> }
                            </span>
                            <span className="logo-sm">
                                <img src={logoLight} alt="" height="42" />
                            </span>
                        </Link>
                    </div>

                    <nav className="navbar-custom">
                        <ul className="list-inline menu-left mb-0">
                            <li className="float-left">
                                <button onClick={ this.sidebarToggle }   className="button-menu-mobile open-left waves-effect">
                                    <i className="mdi mdi-menu"></i>
                                </button>
                            </li>
 
                            <li className="d-none d-sm-block">
                        </li>
                        </ul>
                    </nav>
                </div>
            </React.Fragment>
        );
    }
}

const mapStatetoProps = state => {
    const { is_toggle,is_light } = state.Layout;
    return {  is_toggle,is_light };
}

export default withRouter(connect(mapStatetoProps, { toggleSidebar })(Topbar));















