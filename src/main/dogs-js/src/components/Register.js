import React, { Component } from "react";
import axios from "axios";
import { Button, Form, FormControl, FormGroup, FormLabel } from "react-bootstrap";
import swal from "sweetalert";

export default class Register extends Component {

    constructor(props) {
        super(props);
        this.state = {
            user: {"username": "", "password": "", "confirmPassword": "", "firstName": "", "lastName": ""},
            valid: {"username": true, "password": true, "confirmPassword": true, "firstName": true, "lastName": true}
        };
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
            case "confirmPassword":
                tempValid["confirmPassword"] = document.getElementById("confirmPassword").value === document.getElementById("password").value;
                break;
            case "firstName":
                tempValid["firstName"] = document.getElementById("firstName").value.length !== 0;
                break;
            case "lastName":
                tempValid["lastName"] = document.getElementById("lastName").value.length !== 0;
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
        tempValid["confirmPassword"] = document.getElementById("confirmPassword").value === document.getElementById("password").value;
        tempValid["firstName"] = document.getElementById("firstName").value.length !== 0;
        tempValid["lastName"] = document.getElementById("lastName").value.length !== 0;
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
            delete this.state.user["confirmPassword"];
            console.log(this.state.user);
            axios.post("/register", this.state.user)
                .then(response => {
                    if (response.status === 200) {
                        swal({
                            title: "Registered successfully",
                            icon: "success",
                            closeOnClickOutside: true
                        });
                        this.props.history.push("/");
                    }
                }).catch(error => {
                swal({
                    title: "An error occurred",
                    text: "Please try again",
                    icon: "error",
                });
            });
        } else {
            swal({
                title: "Please fill out every field in the form",
                icon: "warning",
                closeOnClickOutside: true
            });
        }
    };

    render() {
        return (
            <div>
                <h1>Register</h1>
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

                    <FormGroup>
                        <FormLabel>Confirm password</FormLabel>
                        <FormControl id="confirmPassword" value={this.state.user["confirmPassword"]} onChange={(event) => this.handleChangeProperty(event, "confirmPassword")} isInvalid={!this.state.valid["confirmPassword"]} type="password"/>
                        <FormControl.Feedback type="invalid">Passwords must match.</FormControl.Feedback>
                    </FormGroup>

                    <FormGroup>
                        <FormLabel>First name</FormLabel>
                        <FormControl id="firstName" value={this.state.user["firstName"]} onChange={(event) => this.handleChangeProperty(event, "firstName")} isInvalid={!this.state.valid["firstName"]}/>
                        <FormControl.Feedback type="invalid">Please provide a first name.</FormControl.Feedback>
                    </FormGroup>

                    <FormGroup>
                        <FormLabel>Last name</FormLabel>
                        <FormControl id="lastName" value={this.state.user["lastName"]} onChange={(event) => this.handleChangeProperty(event, "lastName")} isInvalid={!this.state.valid["lastName"]}/>
                        <FormControl.Feedback type="invalid">Please provide a last name.</FormControl.Feedback>
                    </FormGroup>
                    <hr/>
                    <Button id="submit" variant="dark" onClick={this.handleSubmit}>Submit</Button>
                </Form>
                <Button style={{"margin-top": "5px"}} id="back" variant="dark" onClick={this.props.history.goBack}>Back</Button>
            </div>
        );
    }
}
