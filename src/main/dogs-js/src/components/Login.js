import React, { Component } from "react";
import axios from "axios";
import Cookies from "universal-cookie";
import { Button, Form, FormControl, FormGroup, FormLabel } from "react-bootstrap";

export default class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            user: {"username": "", "password": ""},
            valid: {"username": true, "password": true}
        };
        this.cookies = new Cookies();
    }

    validateProperty = (property) => {
        let tempValid = {...this.state.valid};
        switch (property) {
            case "username":
                tempValid["username"] = document.getElementById("username").value.length !== 0;
                break;
            case "password":
                tempValid["password"] = document.getElementById("password").value.length >= 8;
                break;
            default:
                break;
        }
        this.setState({valid: tempValid});
    };

    handleChangeProperty = (event, property) => {
        let tempUser = {...this.state.user};
        tempUser[property] = event.target.value;
        this.setState({user: tempUser});
        this.validateProperty(property);
    };

    checkValidation = () => {
        let validated = true;
        let tempValid = {...this.state.valid};
        tempValid["username"] = document.getElementById("username").value.length !== 0;
        tempValid["password"] = document.getElementById("password").value.length >= 8;
        for (let key in tempValid) {
            if (tempValid.hasOwnProperty(key) && tempValid[key] === false) {
                this.validateProperty(key);
                validated = false;
                break;
            }
        }
        return validated;
    };

    handleSubmit = () => {
        if (this.checkValidation()) {
            axios.post("/login", this.state.user)
                .then(response => {
                    this.cookies.set("jwt", response.data["jwt"]);
                    this.props.history.push("/");
                }).catch(error => {
                alert(error.response.data);
            });
        } else {
            alert("Please fill out every field in the form.");
        }
    };

    render() {
        return (
            <div>
                <h1>Login</h1>
                <hr/>
                <Form>
                    <FormGroup>
                        <FormLabel>Username</FormLabel>
                        <FormControl id="username" value={this.state.user["username"]} onChange={(event) => this.handleChangeProperty(event, "username")} isInvalid={!this.state.valid["username"]}/>
                        <FormControl.Feedback id="control" type="invalid">Please provide a username.</FormControl.Feedback>
                    </FormGroup>

                    <FormGroup>
                        <FormLabel>Password</FormLabel>
                        <FormControl id="password" value={this.state.user["password"]} onChange={(event) => this.handleChangeProperty(event, "password")} isInvalid={!this.state.valid["password"]} type="password"/>
                        <FormControl.Feedback type="invalid">Password must be at least 8 characters long.</FormControl.Feedback>
                    </FormGroup>
                    <hr/>
                    <Button onClick={this.handleSubmit} text="Submit"/>
                </Form>
                <Button back onClick={this.props.history.goBack} text="Back"/>
            </div>
        );
    }
}
