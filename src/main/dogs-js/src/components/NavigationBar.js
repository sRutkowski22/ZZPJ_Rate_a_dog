import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Nav, Navbar } from 'react-bootstrap';
import Cookies from "universal-cookie";
import { currentUser } from "../index";
import "./Main.css";

export default class NavigationBar extends Component {

    constructor(props) {
        super(props);
        this.cookies = new Cookies();
    }

    handleLogout = () => {
        this.cookies.remove("jwt");
        window.location.reload();
    }

    renderForUser = () => {
        if (currentUser() !== "") {
            return (
                <Nav className="ml-auto">
                    <Nav.Item>
                        <Nav.Link>
                            <Link to="/account">My profile</Link>
                        </Nav.Link>
                    </Nav.Item>

                    <Nav.Item>
                        <Nav.Link>
                            <Link to="/" onClick={this.handleLogout}>Logout</Link>
                        </Nav.Link>
                    </Nav.Item>

                    <Nav.Item>
                        <Nav.Link>
                            <Link to="/randomDog">Random dog</Link>
                        </Nav.Link>
                    </Nav.Item>
                    {/*<Nav.Item>*/}
                    {/*    <Nav.Link>*/}
                    {/*        <Link to="/randomBreedDog">Random dog by breed</Link>*/}
                    {/*    </Nav.Link>*/}
                    {/*</Nav.Item>*/}
                </Nav>
            );
        } else {
            return <React.Fragment/>;
        }
    }

    renderForGuest = () => {
        if (currentUser() === "") {
            return (
                <Nav className="ml-auto">
                    <Nav.Item>
                        <Nav.Link>
                            <Link to="/login">Login</Link>
                        </Nav.Link>
                    </Nav.Item>

                    <Nav.Item>
                        <Nav.Link>
                            <Link to="/register">Register</Link>
                        </Nav.Link>
                    </Nav.Item>
                </Nav>
            );
        } else {
            return <React.Fragment/>;
        }
    }

    render() {
        return (
            <Navbar expand="lg">
                <Navbar.Brand href="/">Rate a dog</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse id="basic-navbar-nav">
                    {this.renderForGuest()}
                    {this.renderForUser()}
                </Navbar.Collapse>
            </Navbar>
        );
    }
}
