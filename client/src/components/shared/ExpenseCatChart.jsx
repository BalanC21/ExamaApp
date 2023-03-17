import React from "react";
import {Cell, Legend, Pie, PieChart, Tooltip} from "recharts";
import {newGetSWR} from "../../utils/NewFetcher";
import useSWR from "swr";
import ErrorPage from "./ErrorPage";

export default function ExpenseCatChart() {
    const REACT_APP_CRUD_CHART_DATA = process.env.REACT_APP_CRUD_CHART_DATA
    const COLORS = ["#8884d8", "#82ca9d", "#FFBB28", "#FF8042", "#AF19FF"];

    const {data: pieData, error: errorChartData} =
        useSWR(REACT_APP_CRUD_CHART_DATA, newGetSWR, {revalidateOnFocus: false});

    if (errorChartData) return <ErrorPage/>
    if (!pieData) return <div>Loading...</div>;

    function CustomTooltip({active, payload}) {
        if (active) {
            return (
                <div className="custom-tooltip">
                    <label className={"chart-label"}>
                        {`${payload[0].name} : ${payload[0].value}%`}
                    </label>
                </div>
            );
        }
        return null;
    }

    return (
        <div className="expense-category-chart">
            <PieChart width={250} height={300}>
                <Pie
                    data={pieData}
                    color="#000000"
                    dataKey="value"
                    nameKey="name"
                    cx="50%"
                    cy="50%"
                    outerRadius={120}
                    fill="#8884d8">
                    {pieData.map((entry, index) => (
                        <Cell
                            key={`cell-${index}`}
                            fill={COLORS[index % COLORS.length]}
                        />
                    ))}
                </Pie>
                <Tooltip content={<CustomTooltip/>}/>
                <Legend/>
            </PieChart>
        </div>
    );

}
