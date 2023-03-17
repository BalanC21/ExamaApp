import React from 'react';
import "../../App.css";

const IncomePercentageCard = () => {
    return (
        <link href='https://unpkg.com/boxicons@2.1.2/css/boxicons.min.css' rel='stylesheet'>
            <div>
                <div className="cardana">
                    <h4>New clients</h4>
                    <div className="cardana-main">
                        <span>54</span>
                        <p id="green">+18.7%</p>
                    </div>
                </div>

                <div className="cardana">
                    <h4>Invoices overdue</h4>
                    <div className="cardana-main">
                        <span>6</span>
                        <p id="red">+2.7%</p>
                    </div>
                </div>
            </div>
        </link>
    )
};

export default IncomePercentageCard;
