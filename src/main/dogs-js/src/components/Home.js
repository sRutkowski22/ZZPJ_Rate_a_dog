import React, { Component } from "react";
import { currentUser } from "../index";

export default class Home extends Component {

    greeting = () => {
        if (currentUser() !== "") {
            return currentUser();
        } else {
            return "guest";
        }
    }

    render() {
        return (
            <div>
                <h1>Welcome, {this.greeting()}</h1>
            </div>
        )
    }
}
