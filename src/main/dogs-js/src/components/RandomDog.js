import React, { Component } from "react";
import axios from "axios";
import {Button} from "react-bootstrap";
import StarRatingComponent from 'react-star-rating-component';

export default class RandomDog extends Component {

    constructor(props) {
        super(props);
        this.state = {
            dogUrl: "",
            rating: 0
        };
    }

    componentDidMount = () => {
        this.getRandomDog();
    };

    getRandomDog = () => {
        axios.get("/dog/random")
            .then(response => {
                this.setState({
                        dogUrl: response.data,
                        rating: 0
                });
            }).catch(error => {
            console.log(error.response);
        });
    };

    changeRating(newRating, oldRating, name) {
        this.setState({
            rating: newRating
        });
    }

    render() {
        const { rating } = this.state;
        return (
            <div>
                <h1>Random dog</h1>
                <div className="image-div">
                    <img className="image-dog" src={this.state.dogUrl} />
                    <div class="ratings">
                        <StarRatingComponent
                            class="rating"
                            starCount={5}
                            value={rating}
                            onStarClick={this.changeRating.bind(this)} />
                    </div>
                    <div class="buttons">
                        <Button name="rate-button"  >Rate</Button>
                        <div class="divider" />
                        <Button name="confirm-button" onClick={() => this.getRandomDog()}>Next dog</Button>
                    </div>
                </div>
            </div>
        )
    }
}
