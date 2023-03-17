import React from 'react';
import {Bar, BarChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis} from 'recharts';
import useSWR from "swr";
import {newGetSWR} from "../../../../utils/NewFetcher";
import ErrorPage from "../../../shared/ErrorPage";

export default function ApexChart() {
    const REACT_APP_CRUD_TOOLTIP_DATA = process.env.REACT_APP_CRUD_TOOLTIP_DATA;

    const {data: toolTip, error: errorChartData} =
        useSWR(REACT_APP_CRUD_TOOLTIP_DATA, newGetSWR, {revalidateOnFocus: false});
    const CustomTooltip = ({active, payload, label}) => {
        if (active && payload && payload.length) {
            return (
                <div className="custom-tooltip">
                    <p className="label">{`${label} : ${payload[0].value}`}</p>
                </div>
            );
        }

        return null;
    };

    if (errorChartData) return <ErrorPage/>
    if (!toolTip) return <div>Loading...</div>;

    return (
        <ResponsiveContainer width="60%" aspect={3}>
            <BarChart
                width={500}
                height={300}
                data={toolTip}
                margin={{
                    top: 50,
                    right: 30,
                    left: 20,
                    bottom: 5,
                }}>

                <CartesianGrid/>
                <XAxis dataKey="month" tick={{stroke: "red"}}/>
                <YAxis tick={{stroke: "green"}}/>
                <Tooltip content={<CustomTooltip/>}/>
                <Bar dataKey="monthExpenseSum" barSize={50} fill="#8884d8"/>
            </BarChart>
        </ResponsiveContainer>
    );
}
