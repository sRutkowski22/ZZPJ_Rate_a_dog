import React, { Component } from "react";
import { Button, Form, FormControl, FormGroup, FormLabel } from "react-bootstrap";
import axios from "axios";
import Cookies from "universal-cookie";
import {currentUser, jwtHeader} from "../index";

export default class EditAccount extends Component {

    constructor(props) {
        super(props);
        this.state = {
            user: {},
            valid: {"firstName": true, "lastName": true},
            loaded: false
        };
        this.cookies = new Cookies();
    }

    componentDidMount = () => {
        axios.get("/account/" + currentUser(), jwtHeader())
            .then(response => {
                this.setState({
                    user: response.data,
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
            axios.put("/account/" + currentUser(), this.state.user, jwtHeader())
                .then(response => {
                    this.cookies.set("jwt", response.data["jwt"]);
                    this.props.history.push("/");
                }).catch(error => {
                console.log(error.response);
            });
        } else {
            alert("Please fill out every field in the form.");
        }
    };

    renderForm = () => {
        if (this.state.loaded) {
            return (
                <Form>
                    <FormGroup>
                        <FormLabel>Username</FormLabel>
                        <FormControl disabled={true} value={this.state.user["username"]}/>
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
                    <Button onClick={this.handleSubmit} text="Submit"/>
                </Form>
            );
        }
    }

    render() {
        return (
            <div>
                <h1>Login</h1>
                <hr/>
                {this.renderForm()}
                <Button back onClick={this.props.history.goBack} text="Back"/>
            </div>
        );
    }
}
