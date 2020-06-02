import React, { Component } from "react";
import axios from "axios";
import {Button} from "react-bootstrap";
import StarRatingComponent from 'react-star-rating-component';
import {currentUser, jwtHeader} from "../index";
import swal from 'sweetalert';

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

    changeRating(newRating) {
        this.setState({
            rating: newRating
        });
    }

    loginExpected() {
        this.props.history.push("/login");
        swal({
            title: "You must be logged in",
            icon: "info",
            closeOnClickOutside: true
        })
    }

    rateDog = () => {
        if(!(currentUser() === "")) {
            if (this.state.rating > 0) {
                axios.post("/review", {
                    "url": this.state.dogUrl,
                    "rating": this.state.rating,
                    "username": currentUser()
                }, jwtHeader())
                    .then(
                        swal({
                            title: "Your rating was successfully saved",
                            icon: "success",
                            closeOnClickOutside: true
                        })
                    )
                    .catch(error => {
                        console.log(error.response);
                    })
            }
        } else {
            this.loginExpected();
        }
    };

    addToFavorites = () => {
        if(!(currentUser() === "")) {
        axios.put("/favorite/" + currentUser(), {
            "url": this.state.dogUrl
        }, jwtHeader())
            .then(
                swal({
                    title: "Picture was added to favorites",
                    icon: "success",
                    closeOnClickOutside: true
                })
            )
            .catch(error => {
                console.log(error.response);
            })
        } else {
            this.loginExpected();
        }
    };

    render() {
        const { rating } = this.state;
        return (
            <div>
                <div className="image-div">
                    <img alt="img" className="image-dog" src={this.state.dogUrl} />
                    <div class="ratings">
                        <StarRatingComponent
                            class="rating"
                            starCount={5}
                            value={rating}
                            onStarClick={this.changeRating.bind(this)} />
                    </div>
                    <div class="buttons">
                        <Button variant="dark" name="rate-button" onClick={() => this.rateDog()} >Rate</Button>
                        <div class="divider" />
                        <Button variant="dark" name="confirm-button" onClick={() => this.getRandomDog()}>Next dog</Button>
                    </div>
                    <div>
                        <Button variant="dark" onClick={() => this.addToFavorites()}>Add to favorites</Button>
                    </div>
                </div>
            </div>
        )
    }
}
