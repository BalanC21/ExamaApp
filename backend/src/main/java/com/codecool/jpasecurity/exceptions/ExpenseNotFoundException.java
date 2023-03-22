package com.codecool.jpasecurity.exceptions;

public class ExpenseNotFoundException extends RuntimeException {
    public ExpenseNotFoundException(Long id) {
        super("Expense with Id: " + id + " not found");
    }
}
