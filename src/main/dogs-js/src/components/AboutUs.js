import React, {Component} from "react";
import ReactPlayer from "react-player";
import {Link} from "react-router-dom";

export default class AboutUs extends Component {

    constructor(props) {
        super(props);
        this.state = {
            playing: false
        }
    }

    handleOnReady = () => setTimeout(() => this.setState({
        playing: true
    }), 200);

    render() {
        return (
        <div>
            <h1>Authors</h1>
            <div>
                <a href="https://github.com/50Cube">50Cube</a>
            </div>
            <div>
                <a href="https://github.com/pkomuda">pkomuda</a>
            </div>
            <div>
                <a href="https://github.com/GabroVV">GabroVV</a>
            </div>
            <div>
                <a href="https://github.com/sRutkowski22">sRutkowski22</a>
            </div>
            <label style={{"padding-top": "20px"}}>Volume up!</label>
            <ReactPlayer style={{"display": "none"}} onReady={this.handleOnReady} url='https://www.youtube.com/watch?v=5QH_S8IuWoU' playing={this.state.playing} />
        </div>
        )
    }
}