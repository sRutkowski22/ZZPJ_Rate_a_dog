import React, { Component } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import { Container } from "react-bootstrap";
import NavigationBar from "./components/NavigationBar";
import Home from "./components/Home";
import Login from "./components/Login";
import Register from "./components/Register";
import NotFound from "./components/NotFound";
import EditAccount from "./components/EditAccount";
import RatedDogs from "./components/RatedDogs";
import RandomBreedDog from "./components/RandomBreedDog";
import FavoriteDogs from "./components/FavoriteDogs";
import About from "./components/About";

export default class App extends Component {

    render() {
        return (
            <React.Fragment>
                <Router basename="app">
                    <NavigationBar/>
                    <Container>
                        <Switch>
                            <Route exact path="/" component={Home}/>
                            <Route exact path="/login" component={Login}/>
                            <Route exact path="/register" component={Register}/>
                            <Route exact path="/account" component={EditAccount}/>
                            <Route exact path="/ratedDogs" component={RatedDogs}/>
                            <Route exact path="/randomBreedDog" component={RandomBreedDog}/>
                            <Route exact path="/favoriteDogs" component={FavoriteDogs} />
                            <Route exact path="/about" component={About} />
                            <Route component={NotFound}/>
                        </Switch>
                    </Container>
                </Router>
            </React.Fragment>
        );
    }
}
