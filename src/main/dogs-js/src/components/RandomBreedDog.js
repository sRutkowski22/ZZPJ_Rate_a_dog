import React, { Component } from "react";
import axios from "axios";
import {Button} from "react-bootstrap";
import Select from 'react-select'
import {currentUser, jwtHeader} from "../index";
import swal from "sweetalert";
import StarRatingComponent from "react-star-rating-component";
export default class RandomDog extends Component {

    constructor(props) {
        super(props);
        this.state = {
            breedList: [],
            breedListOptions: [],
            breed: {},
            dogUrl: "",
            rating: 0,
            averageRating: 0
        };

    }

    componentDidMount = () => {
        this.getBreedList();
    };

    fillOptions = () => {
        let breedListOptionsLocal = [];
        this.state.breedList.sort();
        this.state.breedList.forEach(function (item,index) {
            breedListOptionsLocal.push({value:item, label:item})
        });
        this.setState({
            breedListOptions: breedListOptionsLocal,
            breed: breedListOptionsLocal[0]
        }
        )
    };

    changeRating(newRating) {
        this.setState({
            rating: newRating
        });
    }

    handleDogChange = breed => {
        this.setState(
            { breed },
            () => console.log(`Option selected:`, this.state.breed)
        );
    };

    getBreedList = () => {
        axios.get("/dog/breedlist", jwtHeader())
            .then(response => {
                this.setState({
                    breedList: response.data
                });
            this.fillOptions();
            }).catch(error => {
            console.log(error.response);
        });
    };

    getRandomDog = (breed) => {
        axios.get("/dog/breed/random/" + breed, jwtHeader())
            .then(response => {
                this.setState({
                    dogUrl: response.data,
                    rating: 0,
                    averageRating: 0
                });
            }).then(() => {
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

    rateDog = () => {
        if(this.state.rating > 0) {
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
    };

    addToFavorites = () => {
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
    };

    render() {
        const {breed} = this.state;
        const {rating} = this.state;
        return (
            <div>
                <h4>Choose the breed</h4>
                <Select id="breed-select" options={this.state.breedListOptions} value = {breed} onChange={this.handleDogChange}/>
                <div className="image-div">
                    <label style={{"padding-top": "20px"}} className="labels">Average rating</label>
                    <div className="ratings">
                        <StarRatingComponent
                            starCount={5}
                            value={this.state.averageRating}
                            editing={false}/>
                    </div>
                    <img className="image-dog" src={this.state.dogUrl} alt="Loading" />
                    <label className="labels">Your rating</label>
                    <div className="ratings">
                        <StarRatingComponent
                            class="rating"
                            starCount={5}
                            value={rating}
                            onStarClick={this.changeRating.bind(this)}/>
                    </div>
                    <div className="buttons">
                        <Button variant="dark" name="rate-button" onClick={() => this.rateDog() } disabled={this.state.dogUrl === ""}>Rate</Button>
                        <div className="divider" />
                        <Button variant="dark" name="confirm-button" onClick={() => this.getRandomDog(this.state.breed.value)}>Next dog</Button>
                    </div>
                    <div>
                        <Button variant="dark" onClick={() => this.addToFavorites()} disabled={this.state.dogUrl === ""}>Add to favorites</Button>
                    </div>
                </div>
            </div>
        )
    }
}
