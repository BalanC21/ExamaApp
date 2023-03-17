import React from 'react';
import {NavLink} from "react-router-dom";
import LogInForm from "./components/LogInForm";

const LogInPage = () => {

    return (
        <React.Fragment>
            <div className="background">
                <div className="shape"></div>
                <div className="shape"></div>
            </div>
            <div className="container">
                <h3>Login Here</h3>
                <LogInForm/>
                <div className="social">
                    <button className="google"> Google</button>
                    <button className="facebook"> Facebook</button>
                </div>
                <div className="sign-up-container">
                    <div className="actions-container">
                        <p>Don't have an account?</p>
                        <NavLink to={"/signup"} className="sign-up">SignUp</NavLink>
                    </div>
                </div>
            </div>
        </React.Fragment>
    )
}

export default LogInPage;
