import React, { Component } from "react";
import axios from "axios";
import Cookies from "universal-cookie";
import {Button} from "react-bootstrap";

export default class RandomDog extends Component {

    constructor(props) {
        super(props);
        this.state = {
            dogUrl: ""
        };

    }

    componentDidMount = () => {
        this.getRandomDog();
    }

    getRandomDog = () => {
        axios.get("/dog/random")
            .then(response => {
                this.setState({
                        dogUrl: response.data
                });
            }).catch(error => {
            console.log(error.response);
        });
    }

    render() {
        return (
            <div>
                <h1>Random dog</h1>
                <div className="image-div">
                    <img className="image-dog" src={this.state.dogUrl} />
                </div>
                <Button variant="dark" onClick={() => this.getRandomDog()}>Next dog</Button>
            </div>
        )
    }
}
