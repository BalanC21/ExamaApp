import React from 'react';
import {NavLink} from "react-router-dom";

const NavBar = () => {
    return (
        <header className="nav-bar">
            <NavLink to={"/"} className={"nav-title"}>MoneyWise</NavLink>
            <div className="nav-links">
                <NavLink to={"/logIn"} className="nav-link">LogIn</NavLink>
                <NavLink to={"/signup"} className="nav-link">Register</NavLink>
                <a href="#" className="nav-link">Notifications</a>
            </div>
        </header>
    )
};

export default NavBar;
