import {useState} from 'react';
import "../../../../App.css"
import {mutate} from "swr";
import {newPatchSWR, newPostSWR} from "../../../../utils/NewFetcher";
import {notifyAddExpenseError, notifyAddExpenseSuccess} from "../../../shared/Toasts";

const ExpenseModal = ({categories}) => {
    const REACT_APP_CRUD_EXPENSES = process.env.REACT_APP_CRUD_EXPENSES
    const REACT_APP_CRUD_CHART_DATA = process.env.REACT_APP_CRUD_CHART_DATA
    const REACT_APP_CRUD_TOOLTIP_DATA = process.env.REACT_APP_CRUD_TOOLTIP_DATA;
    const REACT_APP_CRUD_REVENUE = process.env.REACT_APP_CRUD_REVENUE;
    const revenue = {
        amount: 0,
        isRecursive: false,
        renewalDayOfMonth: 1
    }

    const [newExpense, setNewExpense] = useState({
        amount: 0,
        description: "",
        expenseType: "",
        localDate: ""
    });

    function handle(e) {
        const newData = {...newExpense};
        newData[e.target.id] = e.target.value;
        setNewExpense(newData)
    }

    let submit = (element) => {
        element.preventDefault();
        newPostSWR(REACT_APP_CRUD_EXPENSES, newExpense).then(async () => {
            await mutate(REACT_APP_CRUD_EXPENSES, false)
            await mutate(REACT_APP_CRUD_CHART_DATA, false)
            await mutate(REACT_APP_CRUD_TOOLTIP_DATA, false)
            revenue.amount = newExpense.amount
            notifyAddExpenseSuccess()
        }).then(async () => {
            revenue.amount = newExpense.amount;
            await newPatchSWR(REACT_APP_CRUD_REVENUE, revenue)
        })
            .catch((r) => {
                console.log("Submit Expense Modal")
                console.log(r.response.data)
                console.log(r)
                notifyAddExpenseError()
            })
    }

    return (
        <div className={"modal-content"}>
            <h2>Add Expense</h2>
            <form onSubmit={(e) => submit(e)}>
                <label htmlFor="localDate">Select Date</label>
                <input type="date" id={"localDate"} min="2017-04-01" className="input input-dates"
                       onChange={(e) => handle(e)}/>
                <label htmlFor="description">Description</label>
                <input type="text" id={"description"} className="input input-description"
                       onChange={(e) => handle(e)}/>
                <label htmlFor={"amount"}>Amount</label>
                <input type="number" id={"amount"} className="input input-amount"
                       onChange={(e) => handle(e)}/>
                <label htmlFor="expenseType">Category</label>
                <select className="input input-type" id={"expenseType"} onChange={(e) => handle(e)}>
                    {categories.map((element, i) => (
                        <option key={i} value={element}>{element}</option>
                    ))}
                </select>
                <button className={"submit-btn"}>Submit</button>
            </form>
        </div>
    )
};

export default ExpenseModal;
