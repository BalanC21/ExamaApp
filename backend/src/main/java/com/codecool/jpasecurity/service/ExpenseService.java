package com.codecool.jpasecurity.service;

import com.codecool.jpasecurity.enums.ExpenseType;
import com.codecool.jpasecurity.model.Expense;
import com.codecool.jpasecurity.model.User;
import com.codecool.jpasecurity.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository repository;
    private final GetUsers getUsers;

    public ExpenseService(ExpenseRepository repository, GetUsers getUsers) {
        this.repository = repository;
        this.getUsers = getUsers;
    }

    public Iterable<Expense> findAll() {
        return repository.findAll();
    }

    public void addExpense(Expense expense) {
        User user = getUsers.getUser();

        if (expense.getLocalDate() == null) {
            expense.setLocalDate(LocalDate.now());
        }
        expense.setOwner(user);
        repository.save(expense);
    }

    public Expense findById(Long id) {
        return repository.findById(id).orElseThrow(NoClassDefFoundError::new);
    }

    public Expense updateExpense(Long id, Expense expense) {
        Expense toUpdateExpense = repository.findById(id).orElseThrow(NoClassDefFoundError::new);
        toUpdateExpense.setAmount(expense.getAmount());
        toUpdateExpense.setLocalDate(expense.getLocalDate());
        toUpdateExpense.setDescription(expense.getDescription());
        toUpdateExpense.setExpenseType(expense.getExpenseType());
        repository.save(toUpdateExpense);
        return expense;
    }

    public List<Expense> filterExpensesByType(ExpenseType expense) {
        if (repository.findAllByExpenseType(expense).isPresent()) {
            return repository.findAllByExpenseType(expense).get();
        }
        return new ArrayList<>();

    }

    public List<Expense> filterExpensesByAmount(long amount) {
        if (repository.findAllByAmount(amount).isPresent()) {
            return repository.findAllByAmount(amount).get();
        }
        return new ArrayList<>();
    }

    public List<Expense> getAllExpensesByUser() {
        User owner = getUsers.getUser();
        return repository.findAllByOwner_UserId(owner.userId);
    }

    public boolean deleteById(Long id) {
        repository.deleteById(id);
        return true;
    }
}
