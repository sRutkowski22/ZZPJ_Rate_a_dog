import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Nav, Navbar } from 'react-bootstrap';
import "./NavigationBar.css";

export default class NavigationBar extends Component {

    render() {
        return (
            <Navbar expand="lg">
                <Navbar.Brand>Notice board</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="ml-auto">
                        <Nav.Item>
                            <Nav.Link>
                                <Link to="/">Home</Link>
                            </Nav.Link>
                        </Nav.Item>

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
                </Navbar.Collapse>
            </Navbar>
        );
    }
}
