import React from 'react';
import "../../../App.css"
import RevenueModal from "../../Revenue/RevenueModal";
import useSWR from "swr";
import {newGetSWR} from "../../../utils/NewFetcher";
import ErrorPage from "../../shared/ErrorPage";


const CardComponent = () => {
    const REACT_APP_CRUD_REVENUE = process.env.REACT_APP_CRUD_REVENUE
    const {isLoading, data: revenue, error} =
        useSWR(REACT_APP_CRUD_REVENUE, newGetSWR, {revalidateOnFocus: false});

    if (error) return <ErrorPage/>
    if (isLoading) return <div>Loading...</div>;

    return (
        <div className="cards-container">
            <RevenueModal/>
            <div className="row">
                <div>
                    <div className="card bg-c-blue">
                        <div style={{display: "flex", justifyContent: "start", flexDirection: "column"}}>
                            <div style={{display: "inherit"}}>
                                <h2 className="m-b-20">Total Revenue:</h2>
                                <h2 className="text-right"><i></i><span>{revenue.amount}</span></h2>
                            </div>
                            <p>Completed Orders: <span>251</span></p>
                        </div>
                    </div>
                </div>

                <div>
                    <div className="card bg-c-green">
                        <div style={{display: "flex", justifyContent: "start", flexDirection: "column"}}>
                            <div style={{display: "inherit"}}>
                                <h2 className="m-b-20">Money Gained </h2>
                                <h2 className="text-right"><i></i><span> $786</span></h2>
                            </div>
                            <p>Completed Orders: <span>351</span></p>
                        </div>
                    </div>
                </div>

                <div>
                    <div className="card bg-c-yellow">
                        <div style={{display: "flex", justifyContent: "start", flexDirection: "column"}}>
                            <div style={{display: "inherit"}}>
                                <h2 className="m-b-20">Pending Transactions: </h2>
                                <h2 className="text-right"><i></i><span>486</span></h2>
                            </div>
                            <p>Total Orders: <span>8</span></p>
                        </div>
                    </div>
                </div>

                <div>
                    <div className="card bg-c-pink">
                        <div style={{display: "flex", justifyContent: "start", flexDirection: "column"}}>
                            <div style={{display: "inherit"}}>
                                <h2 className="m-b-20">Failed Transactions: </h2>
                                <h2 className="text-right"><i></i><span> 13 </span></h2>
                            </div>
                            <p>Total Orders: <span>21</span></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
};

export default CardComponent;
