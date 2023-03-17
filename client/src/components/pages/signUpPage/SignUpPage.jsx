import React, {useState} from 'react';
import "../../../App.css"
import axios from "axios";
import {NavLink, useNavigate} from "react-router-dom";

const SignUpPage = () => {
    const navigate = useNavigate();
    const [newUser, setNewUser] = useState({
        username: "",
        password: "",
        email: "",
        role: ["ROLE_USER"]
    })

    function handle(e) {
        const newData = {...newUser};
        newData[e.target.id] = e.target.value;
        setNewUser(newData)
    }


    function submit(e) {
        e.preventDefault();
        axios
            .post("http://localhost:8888/api/auth/signup", {
                username: newUser.email.split("@").at(0),
                email: newUser.email,
                password: newUser.password,
                role: ["ROLE_USER"]
            },)
            .then(navigate("/login"));
    }

    return (
        <React.Fragment>
            <div className="background-sign-up">
                <div className="shape-sign-up"></div>
                <div className="shape-sign-up"></div>
            </div>
            <div className="container">
                <h3>SignUp</h3>
                <form onSubmit={(e) => submit(e)}>
                    <label htmlFor="email">Emai</label>
                    <input type="text" placeholder="Email" onChange={(e) => handle(e)} id="email"/>

                    <label htmlFor="password">Password</label>
                    <input type="password" placeholder="password" onChange={(e) => handle(e)} id="password"/>
                    <label htmlFor="password">Confirm Password</label>
                    <input type="password" placeholder="confirm password" id="confirm-password"/>
                    <button className="sign-up-button">SignUp</button>
                </form>
                <div className="social">
                    <button className="google"> Google</button>
                    <button className="facebook"> Facebook</button>
                </div>
                <div className="sign-up-container">
                    <div className="actions-container">
                        <p>Have an account?</p>
                        <NavLink to={"/login"} className="login">LogIn</NavLink>
                    </div>
                </div>
            </div>
        </React.Fragment>
    )
};

export default SignUpPage;
