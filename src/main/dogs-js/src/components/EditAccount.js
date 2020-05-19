import React, { Component } from "react";
import {Button, Form, FormControl, FormGroup, FormLabel} from "react-bootstrap";
import axios from "axios";
import Cookies from "universal-cookie";
import {currentUser, jwtHeader} from "../index";

export default class EditAccount extends Component {

    constructor(props) {
        super(props);
        this.state = {
            user: {},
            valid: {"firstName": true, "lastName": true, "password": true, "confirmPassword": true},
            changePassword: false,
            loaded: false
        };
        this.cookies = new Cookies();
    }

    componentDidMount = () => {
        axios.get("/account/" + currentUser(), jwtHeader())
            .then(response => {
                let tempUser = response.data;
                tempUser["password"] = "";
                tempUser["confirmPassword"] = "";
                this.setState({
                    user: tempUser,
                    loaded: true
                });
            }).catch(error => {
            console.log(error.response);
            this.props.history.goBack();
        });
    }

    validateProperty = (property) => {
        let tempValid = {...this.state.valid};
        switch (property) {
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
        if (this.state.changePassword) {
            tempValid["password"] = document.getElementById("password").value.length >= 8;
            tempValid["confirmPassword"] = document.getElementById("confirmPassword").value === document.getElementById("password").value;
        }
        tempValid["firstName"] = document.getElementById("firstName").value.length !== 0;
        tempValid["lastName"] = document.getElementById("lastName").value.length >= 8;
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
            if (!this.state.changePassword) {
                delete this.state.user["password"];
            }
            delete this.state.user["confirmPassword"];
            axios.put("/account/" + currentUser(), this.state.user, jwtHeader())
                .then(response => {
                    alert(response.status);
                    this.props.history.push("/");
                }).catch(error => {
                alert(error.response.data);
            });
        } else {
            alert("Please fill out every field in the form.");
        }
    };

    handleSwitchChangePassword = () => {
        this.setState({changePassword: !this.state.changePassword});
    }

    renderChangePasswordForm = () => {
        if (this.state.changePassword) {
            return (
                <React.Fragment>
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
                </React.Fragment>
            );
        }
    }

    renderForm = () => {
        if (this.state.loaded) {
            return (
                <Form>
                    <Form.Switch id="changePasswordSwitch" label="Change password" checked={this.state.changePassword} onChange={this.handleSwitchChangePassword} style={{"margin-bottom": "0.75em"}}/>
                    {this.renderChangePasswordForm()}

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
                    <Button variant="dark" onClick={this.handleSubmit}>Edit profile</Button>
                </Form>
            );
        }
    }

    render() {
        return (
            <div>
                <h1>{currentUser()}'s profile</h1>
                <hr/>
                {this.renderForm()}
                <Button variant="dark" onClick={this.props.history.goBack}>Back</Button>
            </div>
        );
    }
}
