import React, {Component} from "react";
import axios from "axios";
import {currentUser, jwtHeader} from "../index";

export default class FavoriteDogs extends Component {

    constructor(props) {
        super(props);
        this.state = {
            dogs: []
        }
    }

    componentDidMount = () => {
        axios.get("/favorites/" + currentUser(), jwtHeader())
            .then(response => {
                this.setState({
                    dogs: response.data
                })
            }).catch(error => {
                console.log(error.response);
        })
    };

    render() {
        const pictures = [];
        for(let i=0; i<this.state.dogs.length; i++)
            pictures.push(<img style={{padding: "10px"}} src={this.state.dogs[i]} alt="img" />);
        return (
            <div style={{"text-align": "center", "max-width": "500px"}}>
                <h1 style={{"padding-bottom": "30px"}}>My favorite dogs</h1>
                {pictures}
            </div>
        )
    }
}