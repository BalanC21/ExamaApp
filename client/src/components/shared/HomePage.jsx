import React from 'react';
import "../../App.css"
import NavBar from "./NavBar";
import {NavLink, Outlet} from "react-router-dom";

const HomePage = () => {
    return (
        <React.Fragment>
            <NavBar/>

            <div className="menu" tabIndex="0">
                <div className="nav-menu-trigger"></div>
                <header className="avatar">
                    <img src="https://s3.amazonaws.com/uifaces/faces/twitter/kolage/128.jpg"/>
                    <h2>{localStorage.getItem("username")}</h2>
                </header>
                <div className={"navigation-links"}>
                    <NavLink to={"/expenses"} tabIndex="0"
                             className="nav-link-side icon-dashboard"><span
                        className={"span-nav"}>Expenses Tracker</span></NavLink>
                    <NavLink tabIndex="0" className="nav-link-side icon-dashboard"
                             to={"/dashboard"}><span className={"span-nav"}>Payment Status</span></NavLink>
                    <NavLink to={"/contacts"} tabIndex="0" className="nav-link-side icon-users"><span
                        className={"span-nav"}>Contacts</span></NavLink>
                    <NavLink tabIndex="0"
                             className="nav-link-side icon-customers"><span
                        className={"span-nav"}>OverView</span></NavLink>
                    <NavLink tabIndex="0" className="nav-link-side icon-settings"><span
                        className={"span-nav"}>Settings</span></NavLink>
                    <NavLink tabIndex="0" className="nav-link-side icon-settings"><span
                        className={"span-nav"}>Buckets</span></NavLink>
                </div>
            </div>
            <Outlet/>
        </React.Fragment>
    )
};

export default HomePage;
