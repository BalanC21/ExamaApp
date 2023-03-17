import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";
import {logInPost} from "../../../../utils/NewFetcher";

const LogInForm = () => {
    const REQUEST_TOKEN = process.env.REACT_APP_TOKEN
    const navigate = useNavigate();

    const [data, setData] = useState({
        username: "",
        password: "",
    })

    function handle(e) {
        const newData = {...data};
        newData[e.target.id] = e.target.value;
        setData(newData)
    }

    let submit = async (e) => {
        e.preventDefault();
        logInPost(REQUEST_TOKEN, data)
            .then((r) => setToken(r)).then(() => navigate("/"))
    }

    async function setToken(response) {
        await localStorage.clear();
        await localStorage.removeItem("token")
        localStorage.setItem("token", response);
        window.dispatchEvent(new Event("storage"))
    }

    return (
        <>
            <form onSubmit={(e) => submit(e)}>
                <label htmlFor="username">Username</label>
                <input type="text" onChange={(e) => handle(e)} placeholder="Email or Phone" id="username"/>

                <label htmlFor="password">Password</label>
                <input type="password" id="password" onChange={(e) => handle(e)} placeholder="Password"/>

                <button className="log-in-button">LogIn</button>
            </form>
        </>
    )
};

export default LogInForm;
