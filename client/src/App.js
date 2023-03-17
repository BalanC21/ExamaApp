import './App.css';
import HomePage from "./components/shared/HomePage";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import LogInPage from "./components/pages/logInPage/LogInPage";
import SignUpPage from "./components/pages/signUpPage/SignUpPage";
import CardComponent from "./components/pages/DashboardPage/CardComponent";
import React, {useState} from "react";
import ExpensePage from "./components/pages/ExpensePage/ExpensePage";
import ContactsPage from "./components/pages/ContactsPage/ContactsPage";
import IndexPage from "./components/pages/homePage/IndexPage";


function App() {
    const [logged] = useState(true);

    return logged ? (
        <>
            <BrowserRouter>
                <Routes>
                    <Route path={"/"} element={<HomePage/>}>
                        <Route path="/" element={<IndexPage/>}/>
                        <Route path="/expenses" element={<ExpensePage/>}/>
                        <Route path="/dashboard" element={<CardComponent/>}/>
                        <Route path="/contacts" element={<ContactsPage/>}/>
                    </Route>
                    <Route path="/login" element={<LogInPage/>}/>
                    <Route path="/signup" element={<SignUpPage/>}/>
                    {/*<Route path="/get-transaction-details/:id" element={<ElementName/>}/>*/}
                </Routes>
            </BrowserRouter>
        </>
    ) : <SignUpPage/>

}

export default App;
