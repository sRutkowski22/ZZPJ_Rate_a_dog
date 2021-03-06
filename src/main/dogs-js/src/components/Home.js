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
            rating: 0,
            averageRating: 0,
            loggedUser: currentUser()
        };
    }

    componentDidMount = () => {
        this.getRandomDog();
    };

    getRandomDog = () => {
        let url = "/dog/random";
        if (currentUser() !== "") {
            url += "/" + currentUser();
        }
        axios.get(url, jwtHeader())
            .then(response => {
                this.setState({
                    dogUrl: response.data,
                    rating: 0,
                    averageRating: 0
                });
            })
            .then(() => {
                this.getAverageRating();
            }).catch(error => {
            console.log(error.response);
        });
    };

    getAverageRating = () => {
        axios.post("/reviews/average", {"url": this.state.dogUrl}, jwtHeader())
            .then(response => {
            this.setState({
                averageRating: response.data
            })
        }).catch(error => {
            console.log(error.response);
        })
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
            <div className="image-div">
                <label style={{"padding-top": "20px"}} class="labels">Average rating</label>
                <div class="ratings">
                    <StarRatingComponent
                        starCount={5}
                        value={this.state.averageRating}
                        editing={false}/>
                </div>
                <img alt="Loading" className="image-dog" src={this.state.dogUrl} />
                <label class="labels">Your rating</label>
                <div class="ratings">
                    <StarRatingComponent
                        starCount={5}
                        value={rating}
                        onStarClick={this.changeRating.bind(this)}/>
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
        )
    }
}
