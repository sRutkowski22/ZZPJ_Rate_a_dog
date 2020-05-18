import React, { Component } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import { Container } from "react-bootstrap";
import NavigationBar from "./components/NavigationBar";
import Home from "./components/Home";
import Login from "./components/Login";
import Register from "./components/Register";
import NotFound from "./components/NotFound";

export default class App extends Component {

    render() {
        return (
            <React.Fragment>
                <Router>
                    <NavigationBar/>
                    <Container>
                        <Switch>
                            <Route exact path="/" component={Home}/>
                            <Route exact path="/login" component={Login}/>
                            <Route exact path="/register" component={Register}/>
                            <Route component={NotFound}/>
                        </Switch>
                    </Container>
                </Router>
            </React.Fragment>
        );
    }
}