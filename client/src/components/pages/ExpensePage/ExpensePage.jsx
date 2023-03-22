import React from 'react';
import ExpensesData from "./Components/ExpensesData";
import ExpenseModal from "./Components/ExpenseModal";
import {newGetSWR} from "../../../utils/NewFetcher";
import ErrorPage from "../../shared/ErrorPage";
import useSWR from "swr";
import "../../../App.css";
import ExpenseCatChart from "../../shared/ExpenseCatChart";
import {ToastContainer} from "react-toastify";
import {useAtom} from "jotai";
import {ORDER_CRITERIA} from "../../StateManagement/Store";

const ExpensePage = () => {
    const REACT_APP_CRUD_EXPENSES = process.env.REACT_APP_CRUD_EXPENSES
    const REACT_APP_EXPENSES_ORDERED_BY_AMOUNT_ASC = process.env.REACT_APP_EXPENSES_ORDERED_BY_AMOUNT_ASC
    const REACT_APP_EXPENSES_ORDERED_BY_AMOUNT_DESC = process.env.REACT_APP_EXPENSES_ORDERED_BY_AMOUNT_DESC
    const REACT_APP_CRUD_EXPENSES_CATEGORIES = process.env.REACT_APP_CRUD_EXPENSES_CATEGORIES
    const [orderCriteria, setOrderCriteria] = useAtom(ORDER_CRITERIA);
    const tableHeaders = ["Date", "Description", "Category", "Amount"]

    function getExpenses() {
        switch (orderCriteria) {
            case "asc":
                return REACT_APP_EXPENSES_ORDERED_BY_AMOUNT_ASC
            case "desc":
                return REACT_APP_EXPENSES_ORDERED_BY_AMOUNT_DESC
            default:
                return REACT_APP_CRUD_EXPENSES

        }
    }

    let {data: expenses, error: errorExpenses} =
        useSWR(getExpenses(), newGetSWR, {revalidateOnFocus: false});

    const {data: expensesCategories, error: errorExpensesCategories} =
        useSWR(REACT_APP_CRUD_EXPENSES_CATEGORIES, newGetSWR, {revalidateOnFocus: false});

    if (errorExpensesCategories || errorExpenses) return <ErrorPage/>
    if (!expenses || !expensesCategories) return <div>Loading...</div>;

    async function toggleExpenseOrderCriteria() {
        if (orderCriteria === "asc") {
            setOrderCriteria("desc")
        } else if (orderCriteria === "desc") {
            setOrderCriteria("asc")
        } else {
            setOrderCriteria("asc")
        }
    }

    return (
        <div className={"expense-container"}>
            <ToastContainer/>
            <div className="expenses-table">
                <table className="budget-tracker">
                    <thead>
                    <tr>
                        {tableHeaders.map(element => <th onClick={toggleExpenseOrderCriteria}
                                                         key={element}>{element}</th>)}
                    </tr>
                    </thead>
                    <tbody className="entries">
                    {expenses.map((expense, i) =>
                        <ExpensesData key={i} item={expense} data={expenses} categories={expensesCategories}/>
                    )}
                    </tbody>
                </table>
            </div>
            <ExpenseModal data={expenses} categories={expensesCategories}/>
            <ExpenseCatChart/>
        </div>
    )
};
export default ExpensePage;
