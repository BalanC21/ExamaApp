import React from 'react';
import {NavLink} from "react-router-dom";
// import {NavLink} from "react-router-dom";

const ErrorPage = () => {
    return (
        <>
            <div className="error-box">
                <h1 className="error-title">404</h1>
                <h1 className="error-description">An error occurred!</h1>
                <NavLink className="error-button" to={"/"} a>Back To Home</NavLink>
            </div>
        </>
    )
};

export default ErrorPage;
