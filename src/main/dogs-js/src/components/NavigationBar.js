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
        window.location.replace("/");
    }

    renderForUser = () => {
        if (currentUser() !== "") {
            return (
                <Nav className="ml-auto">
                    <Nav.Item>
                        <Nav.Link>
                            <Link class="links" id="account" to="/account">My profile</Link>
                        </Nav.Link>
                    </Nav.Item>

                    <Nav.Item>
                        <Nav.Link>
                            <Link class="links" to="/randomBreedDog">Random dog</Link>
                        </Nav.Link>
                    </Nav.Item>

                    <Nav.Item>
                        <Nav.Link>
                            <Link class="links" id="favoriteDogs" to="/favoriteDogs">Favorite dogs</Link>
                        </Nav.Link>
                    </Nav.Item>

                    <Nav.Item>
                        <Nav.Link>
                            <Link class="links" to="/ratedDogs">Rated dogs</Link>
                        </Nav.Link>
                    </Nav.Item>

                    <Nav.Item>
                        <Nav.Link>
                            <Link class="links" to="/about">About</Link>
                        </Nav.Link>
                    </Nav.Item>

                    <Nav.Item>
                        <Nav.Link>
                            <Link class="links" id="logout" to="/" onClick={this.handleLogout}>Logout</Link>
                        </Nav.Link>
                    </Nav.Item>
                </Nav>
            );
        }
    }

    renderForGuest = () => {
        if (currentUser() === "") {
            return (
                <Nav className="ml-auto">

                    <Nav.Item>
                        <Nav.Link>
                            <Link class="links" to="/about">About</Link>
                        </Nav.Link>
                    </Nav.Item>

                    <Nav.Item>
                        <Nav.Link>
                            <Link class="links" id="login" to="/login">Login</Link>
                        </Nav.Link>
                    </Nav.Item>

                    <Nav.Item>
                        <Nav.Link>
                            <Link class="links" id="register" to="/register">Register</Link>
                        </Nav.Link>
                    </Nav.Item>
                </Nav>
            );
        }
    }

    render() {
        return (
            <Navbar expand="lg" className="navbar-dark">
                <Navbar.Brand id="home" as={Link} to="/">Rate a dog</Navbar.Brand>
                <Navbar.Toggle id="toggle" aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse id="basic-navbar-nav">
                    {this.renderForUser()}
                    {this.renderForGuest()}
                </Navbar.Collapse>
            </Navbar>
        );
    }
}
