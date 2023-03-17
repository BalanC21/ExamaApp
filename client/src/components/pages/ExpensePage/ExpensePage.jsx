import React from 'react';
import ExpensesData from "./Components/ExpensesData";
import ExpenseModal from "./Components/ExpenseModal";
import {newGetSWR} from "../../../utils/NewFetcher";
import ErrorPage from "../../shared/ErrorPage";
import useSWR from "swr";
import "../../../App.css";
import ExpenseCatChart from "../../shared/ExpenseCatChart";
import {ToastContainer} from "react-toastify";

const ExpensePage = () => {
    const REACT_APP_CRUD_EXPENSES = process.env.REACT_APP_CRUD_EXPENSES
    const REACT_APP_CRUD_EXPENSES_CATEGORIES = process.env.REACT_APP_CRUD_EXPENSES_CATEGORIES
    const tableHeaders = ["Date", "Description", "Category", "Amount"]

    const {data: expenses, error: errorExpenses} =
        useSWR(REACT_APP_CRUD_EXPENSES, newGetSWR, {revalidateOnFocus: false});

    const {data: expensesCategories, error: errorExpensesCategories} =
        useSWR(REACT_APP_CRUD_EXPENSES_CATEGORIES, newGetSWR, {revalidateOnFocus: false});

    if (errorExpensesCategories || errorExpenses) return <ErrorPage/>
    if (!expenses || !expensesCategories) return <div>Loading...</div>;

    return (
        <div className={"expense-container"}>
            <ToastContainer/>
            <div className="expenses-table">
                <table className="budget-tracker">
                    <thead>
                    <tr>
                        {tableHeaders.map(element => <th key={element}>{element}</th>)}
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
