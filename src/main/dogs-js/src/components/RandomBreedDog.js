import React, { Component } from "react";
import axios from "axios";
import {Button} from "react-bootstrap";
import Select from 'react-select'
export default class RandomDog extends Component {

    constructor(props) {
        super(props);
        this.state = {
            breedList: [],
            breedListOptions: [],
            breed: {},
            dogUrl: ""
        };

    }

    componentDidMount = () => {
        this.getBreedList();
    }

    fillOptions = () => {
        let breedListOptionsLocal = []
        this.state.breedList.sort()
        this.state.breedList.forEach(function (item,index) {
            breedListOptionsLocal.push({value:item, label:item})
        })
        this.setState({
            breedListOptions: breedListOptionsLocal,
            breed: breedListOptionsLocal[0]
        }
        )
    }

    handleChange = breed => {
        this.setState(
            { breed },
            () => console.log(`Option selected:`, this.state.breed)
        );
    };

    getBreedList = () => {
        axios.get("/dog/breedlist")
            .then(response => {
                this.setState({
                    breedList: response.data
                });
            this.fillOptions();
            }).catch(error => {
            console.log(error.response);
        });
    }

    getRandomDog = (breed) => {
        axios.get("/dog/breed/random/" + breed)
            .then(response => {
                this.setState({
                        dogUrl: response.data
                });
            }).catch(error => {
            console.log(error.response);
        });
    }

    render() {
        const {breed} = this.state
        return (
            <div>
                <h4>Choose the breed</h4>
                <Select options={this.state.breedListOptions} value = {breed} onChange={this.handleChange}/>
                <div className="image-div">
                    <img className="image-dog" src={this.state.dogUrl} />
                </div>
                <Button onClick={() => this.getRandomDog(this.state.breed.value)}>Next dog</Button>
            </div>
        )
    }
}
