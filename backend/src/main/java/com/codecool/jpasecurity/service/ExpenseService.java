package com.codecool.jpasecurity.service;

import com.codecool.jpasecurity.enums.ExpenseType;
import com.codecool.jpasecurity.exceptions.ExpenseNotFoundException;
import com.codecool.jpasecurity.exceptions.RevenueNotFoundException;
import com.codecool.jpasecurity.model.Expense;
import com.codecool.jpasecurity.model.Revenue;
import com.codecool.jpasecurity.model.User;
import com.codecool.jpasecurity.repository.ExpenseRepository;
import com.codecool.jpasecurity.repository.RevenueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository repository;
    private final GetUsers getUsers;
    private final RevenueRepository revenueRepository;

    public ExpenseService(ExpenseRepository repository, GetUsers getUsers, RevenueRepository revenueRepository) {
        this.repository = repository;
        this.getUsers = getUsers;
        this.revenueRepository = revenueRepository;
    }

    public void addExpense(Expense expense) {
        User user = getUsers.getUser();

        if (expense.getLocalDate() == null) {
            expense.setLocalDate(LocalDate.now());
        }
        expense.setOwner(user);
        repository.save(expense);
    }

    public Expense updateExpense(Long expenseId, Expense modifiedExpense) {
        Expense toUpdateExpense = getExpenseById(expenseId);
        modifyUserRevenueForUpdatingExpense(expenseId, modifiedExpense);

        toUpdateExpense.setAmount(modifiedExpense.getAmount());
        toUpdateExpense.setLocalDate(modifiedExpense.getLocalDate());
        toUpdateExpense.setDescription(modifiedExpense.getDescription());
        toUpdateExpense.setExpenseType(modifiedExpense.getExpenseType());

        repository.save(toUpdateExpense);

        return modifiedExpense;
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

    public boolean deleteById(Long expenseId) {
        modifyUserRevenueForDeletingExpense(expenseId);
        repository.deleteById(expenseId);
        return true;
    }

    private void modifyUserRevenueForDeletingExpense(Long expenseId) {
        Expense toDeleteExpense = getExpenseById(expenseId);
        Revenue ownerRevenue = getRevenueForUser();

        long ownerRevenueInitialAmount = ownerRevenue.getAmount();
        ownerRevenue.setAmount(ownerRevenueInitialAmount + toDeleteExpense.getAmount());

        revenueRepository.save(ownerRevenue);
    }

    private void modifyUserRevenueForUpdatingExpense(Long expenseId, Expense modifiedExpense) {
        Expense toUpdateExpense = getExpenseById(expenseId);
        Revenue ownerRevenue = getRevenueForUser();
        long ownerRevenueInitialAmount = ownerRevenue.getAmount();

        long diff = modifiedExpense.getAmount() - toUpdateExpense.getAmount();

        ownerRevenue.setAmount(ownerRevenueInitialAmount - diff);

        revenueRepository.save(ownerRevenue);
    }

    private Expense getExpenseById(long expenseId) {
        Optional<Expense> expense = repository.findById(expenseId);

        if (expense.isEmpty()) {
            throw new ExpenseNotFoundException(expenseId);
        }

        return expense.get();
    }

    private Revenue getRevenueForUser() {
        User owner = getUsers.getUser();
        Optional<Revenue> ownerRevenue = revenueRepository.findByOwner_Username(owner.getUsername());

        if (ownerRevenue.isEmpty()) {
            throw new RevenueNotFoundException();
        }

        return ownerRevenue.get();
    }
}
