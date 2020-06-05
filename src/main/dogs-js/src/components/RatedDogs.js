import React, {Component} from 'react';
import paginationFactory from "react-bootstrap-table2-paginator";
import BootstrapTable from "react-bootstrap-table-next";
import axios from "axios";
import {jwtHeader} from "../index";

export default class RatedDogs extends Component{

    constructor(props) {
        super(props);
        this.state = {
            currentRow: 0,
            reviews: [],
            columns: [{
                dataField: "url",
                text: "Picture",
                accessor: "urlaccess",
                formatter: this.displayImage
            }, {
                dataField: "breed",
                text: "Breed",
                sort: true

            }, {
                dataField: "rating",
                text: "Rating",
                sort: true
            }, {
                dataField: "username",
                text: "Username",
                sort: true
            },{
                dataField: "creationDate",
                text: "Date",
                sort: true
            }]
        }
    }

    displayImage = (cell) => {
            return <img alt="img" src={cell} width={130} height={130} />;
    };

    componentDidMount = () => {
        axios.get("/reviews", jwtHeader())
            .then(response =>{
                let tempReviews = response.data;

                this.setState({
                    reviews: tempReviews
                });
                for(let review of tempReviews){
                    console.log(review)
                }
            })
    };

    renderTable = () => {
        const sizes = [{
            text: "10", value: 10
        }, {
            text: "25", value: 25
        }, {
            text: "50", value: 50
        }, {
            text: "100", value: 100
        }];
        return(
            <div>
                <BootstrapTable keyField="id" data={this.state.reviews}
                                columns={this.state.columns}
                                pagination={paginationFactory({sizePerPageList: sizes})}
                                bootstrap4={true}/>
            </div>
        )
    };

    render() {
        return(
            <div>
                <h1>Rated Dogs</h1>
                {this.renderTable()}
                </div>
        )
    }

}
