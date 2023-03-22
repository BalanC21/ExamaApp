import React, {useState} from 'react';
import 'react-toastify/dist/ReactToastify.css';

import {newDeleteSWR, newPatchSWR} from "../../../../utils/NewFetcher";
import {mutate} from "swr";
import {
    notifyDeletedExpense,
    notifyDeletedExpenseError,
    notifyUpdatedExpense,
    notifyUpdatedExpenseError
} from "../../../shared/Toasts";

const ExpensesData = ({item, categories}) => {
    const REACT_APP_CRUD_EXPENSES = process.env.REACT_APP_CRUD_EXPENSES
    const REACT_APP_CRUD_CHART_DATA = process.env.REACT_APP_CRUD_CHART_DATA

    const [bool, setBool] = useState(true);
    const [toEditExpense, setToEditExpense] = useState({
        amount: item.amount,
        description: item.description,
        expenseType: item.expenseType,
        localDate: item.localDate
    });

    function displayNone() {
        setBool(false)
    }

    function handle(e) {
        const newData = {...toEditExpense};
        newData[e.target.id] = e.target.value;
        setToEditExpense(newData)
    }

    async function deleteExpense() {
        newDeleteSWR(`${REACT_APP_CRUD_EXPENSES}/${item.id}`)
            .then(async () => {
                await mutate(REACT_APP_CRUD_CHART_DATA, false);
                notifyDeletedExpense();
                displayNone();
            }).catch(() => notifyDeletedExpenseError())
    }

    async function updateExpense() {
        newPatchSWR(`${REACT_APP_CRUD_EXPENSES}/${item.id}`, toEditExpense)
            .then(async () => {
                await mutate(REACT_APP_CRUD_EXPENSES, false);
                await mutate(REACT_APP_CRUD_CHART_DATA, false);
                displayNone()
                notifyUpdatedExpense()
            })
            .catch((r) => {
                console.log("Update Expense")
                console.log(r.response.data)
                console.log(r)
                notifyUpdatedExpenseError()
            })
    }

    return (
        <React.Fragment>
            {bool ? (< tr id={item.id}>
                <td>
                    {/*<input type="date" className="input input-dates" id={"localDate"}*/}
                    {/*       placeholder={item.localDate} onChange={(e) => handle(e)}/>*/}
                    <p>{item.localDate}</p>
                </td>
                <td>
                    <input type="text" className="input input-description" id={"description"}
                           placeholder={item.description} onChange={(e) => handle(e)}/>
                </td>
                <td>
                    <select className="input input-type" id={"expenseType"} onChange={(e) => handle(e)}>
                        <option value={item.expenseType}>{item.expenseType}</option>
                        {categories.map((element, i) => (
                            <option key={i} value={element}>{element}</option>
                        ))}
                    </select>
                </td>
                <td>
                    <input type="number" className="input input-amount" id={"amount"}
                           placeholder={item.amount} onChange={(e) => handle(e)}/>
                </td>
                <td>
                    <button onClick={deleteExpense} className="delete-entry">&#10005;</button>
                </td>
                <td>
                    <button onClick={updateExpense} className="">&#9745;</button>
                </td>
            </tr>) : null}
        </React.Fragment>
    )
};

export default ExpensesData;
