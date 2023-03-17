import React from 'react';
import {Cell, Pie, PieChart} from 'recharts';
import useSWR from "swr";
import {newGetSWR} from "../../../../utils/NewFetcher";
import ErrorPage from "../../../shared/ErrorPage";

const CIRCLE_RADIUS = Math.PI / 180;
const PIE_COLOR = '#00ff00';
const NEEDLE_COLOR = '#d0d000';

const needleData = {
    cx: 150,
    cy: 200,
    iR: 50,
    oR: 100,
    value: 300
}

function needle(value, data, cx, cy, iR, oR, color) {
    let total = 0;

    data.forEach((v) => {
        total += v.value;
    });

    const ang = 180.0 * (1 - value / total);
    const length = (needleData.iR + 2 * needleData.oR) / 3;
    const sin = Math.sin(-CIRCLE_RADIUS * ang);
    const cos = Math.cos(-CIRCLE_RADIUS * ang);
    const r = 5;
    const x0 = needleData.cx + 5;
    const y0 = needleData.cy + 5;
    const xba = x0 + r * sin;
    const yba = y0 - r * cos;
    const xbb = x0 - r * sin;
    const ybb = y0 + r * cos;
    const xp = x0 + length * cos;
    const yp = y0 + length * sin;

    return [
        <circle cx={x0} cy={y0} r={r} fill={color} stroke="none"/>,
        <path d={`M${xba} ${yba}L${xbb} ${ybb} L${xp} ${yp} L${xba} ${yba}`} stroke="#none" fill={color}/>,
    ];
}

export default function NeedlePieChart() {
    const REACT_APP_CRUD_NEEDLE_PIE_DATA = process.env.REACT_APP_CRUD_NEEDLE_PIE_DATA;

    const {data: needlePie, error: needlePieError} =
        useSWR(REACT_APP_CRUD_NEEDLE_PIE_DATA, newGetSWR, {revalidateOnFocus: false})


    if (needlePieError) return <ErrorPage/>
    if (!needlePie) return <div>Loading...</div>;

    needleData.value = needlePie[0].needleValue;

    return (
        <div className={"needle-pie-chart"}>
            <p>Money Left:&nbsp;${needlePie[0].value}</p>
            <PieChart width={400} height={220}>
                <Pie
                    dataKey="value"
                    startAngle={180}
                    endAngle={0}
                    data={needlePie}
                    cx={needleData.cx}
                    cy={needleData.cy}
                    innerRadius={needleData.iR}
                    outerRadius={needleData.oR}
                    fill="#8884d8"
                    stroke="none">

                    {needlePie.map((chartData, index) => (
                        <Cell key={`cell-${chartData.value}`} fill={PIE_COLOR}/>
                    ))}

                </Pie>
                {needle(needleData.value, needlePie, needleData.cx, needleData.cy, needleData.iR, needleData.oR, NEEDLE_COLOR)}
            </PieChart>
            <p>For Now Expenses:&nbsp;{needleData.value}</p>
        </div>
    );
}
