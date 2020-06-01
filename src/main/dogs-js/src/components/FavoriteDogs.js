import React, {Component} from "react";
import axios from "axios";

export default class FavoriteDogs extends Component {

    constructor(props) {
        super(props);
        this.state = {
            dogs: []
        }
    }

    componentDidMount = () => {
        axios.get("/favorite")
            .then(response => {

            })
    };

    render() {
        return (
            <div>
                <h1>HeRb</h1>
            </div>
        )
    }
}