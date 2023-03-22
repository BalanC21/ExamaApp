package com.codecool.jpasecurity.service;

import com.codecool.jpasecurity.dto.ExpenseDTO;
import com.codecool.jpasecurity.enums.ExpenseType;
import com.codecool.jpasecurity.exceptions.ExpenseNotFoundException;
import com.codecool.jpasecurity.exceptions.RevenueNotFoundException;
import com.codecool.jpasecurity.model.Expense;
import com.codecool.jpasecurity.model.Revenue;
import com.codecool.jpasecurity.model.User;
import com.codecool.jpasecurity.repository.ExpenseRepository;
import com.codecool.jpasecurity.repository.RevenueRepository;
import jakarta.transaction.Transactional;
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

    public List<Expense> getExpensesOrderedByAmountAsc() {
        User owner = getUsers.getUser();
        return repository.getAllByOwner_UsernameOrderByAmount(owner.getUsername()).get();
    }

    public List<Expense> getExpensesOrderedByAmountDesc() {
        User owner = getUsers.getUser();
        return repository.getAllByOwner_UsernameOrderByAmountDesc(owner.getUsername()).get();
    }

    public void addExpense(ExpenseDTO expenseDTO) {
        Expense expense = mapToExpense(expenseDTO);
        repository.save(expense);
    }

    @Transactional
    public Expense updateExpense(Long expenseId, ExpenseDTO modifiedExpense) {
        Expense toUpdateExpense = getExpenseById(expenseId);
        modifyUserRevenueForUpdatingExpense(expenseId, modifiedExpense);

        toUpdateExpense.setAmount(modifiedExpense.getAmount());
        toUpdateExpense.setLocalDate(modifiedExpense.getLocalDate());
        toUpdateExpense.setDescription(modifiedExpense.getDescription());
        toUpdateExpense.setExpenseType(modifiedExpense.getExpenseType());

        repository.save(toUpdateExpense);

        return toUpdateExpense;
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

    private Expense mapToExpense(ExpenseDTO expenseDTO) {
        User owner = getUsers.getUser();
        Expense toSaveExpense = new Expense();

        toSaveExpense.setAmount(expenseDTO.getAmount());
        toSaveExpense.setDescription(expenseDTO.getDescription());
        toSaveExpense.setExpenseType(expenseDTO.getExpenseType());

        if (expenseDTO.getLocalDate() == null) {
            toSaveExpense.setLocalDate(LocalDate.now());
        } else {
            toSaveExpense.setLocalDate(expenseDTO.getLocalDate());
        }

        toSaveExpense.setOwner(owner);
        return toSaveExpense;
    }

    @Transactional
    private void modifyUserRevenueForDeletingExpense(Long expenseId) {
        Expense toDeleteExpense = getExpenseById(expenseId);
        Revenue ownerRevenue = getRevenueForUser();

        long ownerRevenueInitialAmount = ownerRevenue.getAmount();
        ownerRevenue.setAmount(ownerRevenueInitialAmount + toDeleteExpense.getAmount());

        revenueRepository.save(ownerRevenue);
    }

    private void modifyUserRevenueForUpdatingExpense(Long expenseId, ExpenseDTO modifiedExpense) {
        Expense toUpdateExpense = getExpenseById(expenseId);
        Revenue ownerRevenue = getRevenueForUser();
        long ownerRevenueInitialAmount = ownerRevenue.getAmount();

        long diff = modifiedExpense.getAmount() - toUpdateExpense.getAmount();

        ownerRevenue.setAmount(ownerRevenueInitialAmount - diff);

        revenueRepository.save(ownerRevenue);
    }

    public Expense getExpenseByIDTest(long expenseId) {
        Optional<Expense> expense = repository.findById(expenseId);

        if (expense.isEmpty()) {
            throw new ExpenseNotFoundException(expenseId);
        }

        return expense.get();
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
