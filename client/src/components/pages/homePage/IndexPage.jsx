import React from 'react';
import ApexChart from "./Components/ApexChart";
import NeedlePieChart from "./Components/NeedlePieChart";
import useSWR from "swr";
import {newGetSWR} from "../../../utils/NewFetcher";
import ErrorPage from "../../shared/ErrorPage";
import RevenueModal from "../../Revenue/RevenueModal";

export default function IndexPage() {
    const REACT_APP_CRUD_NEEDLE_PIE_DATA = process.env.REACT_APP_CRUD_NEEDLE_PIE_DATA;
    const {data: needlePie, error: needlePieError} =
        useSWR(REACT_APP_CRUD_NEEDLE_PIE_DATA, newGetSWR, {revalidateOnFocus: false})


    if (needlePieError) return <ErrorPage/>
    if (!needlePie) return <div>Loading...</div>;
    if (needlePie[0].value === 0) return <RevenueModal/>
    return (
        <>
            <div className="index-container">
                <ApexChart/>
                <NeedlePieChart/>
            </div>
        </>
    )
};
