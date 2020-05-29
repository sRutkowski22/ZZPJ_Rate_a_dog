import React, {Component} from 'react';
import paginationFactory from "react-bootstrap-table2-paginator";
import BootstrapTable from "react-bootstrap-table-next"
export default class RatedDogs extends Component{

    constructor(props) {
        super(props);
        this.state = {
            dogs: [],
            columns: [{
                dataField: "Dog",
                text: "Dog",
                sort: true
            }, {
                dataField: "Breed",
                text: "Breed",
                sort: true

            }, {
                dataField: "Rating",
                text: "Rating",
                sort: true
            }, {
                dataField: "Username",
                text: "Username",
                sort: true
            },{
                dataField: "Date",
                text: "Date",
                sort: true
            }]
        }
    }
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
                <BootstrapTable keyField="id" data={this.state.dogs} columns={this.state.columns} pagination={paginationFactory({sizePerPageList: sizes})} bootstrap4={true}/>
            </div>
        )
    }

    render() {
        return(
            <div>
                <h1>Rated Dogs</h1>
                {this.renderTable()}
                </div>
        )
    }

}
