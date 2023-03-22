package com.codecool.jpasecurity.controller;

import com.codecool.jpasecurity.dto.ExpenseDTO;
import com.codecool.jpasecurity.enums.ExpenseType;
import com.codecool.jpasecurity.model.Expense;
import com.codecool.jpasecurity.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpensesByUsers() {
        List<Expense> expenses = expenseService.getAllExpensesByUser();
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{expenseType}/filter")
    public List<Expense> getExpensesByExpenseType(@PathVariable ExpenseType expenseType) {
        return expenseService.filterExpensesByType(expenseType);
    }

    @GetMapping("/ordered-amount-asc")
    public List<Expense> getExpensesOrderedByAmountAsc() {
        return expenseService.getExpensesOrderedByAmountAsc();
    }

    @GetMapping("/ordered-amount-desc")
    public List<Expense> getExpensesOrderedByAmountDesc() {
        return expenseService.getExpensesOrderedByAmountDesc();
    }

    @GetMapping("/{expenseId}")
    public Expense getExpensesByID(@PathVariable long expenseId) {
        return expenseService.getExpenseByIDTest(expenseId);
    }

    @GetMapping("/expenses-categories")
    public ResponseEntity<List<ExpenseType>> getExpenseTypes() {
        List<ExpenseType> expenseTypes = List.of(ExpenseType.values());
        return ResponseEntity.ok(expenseTypes);
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> addExpense(@Valid @RequestBody ExpenseDTO expenseDTO) {
        expenseService.addExpense(expenseDTO);
        return ResponseEntity.ok(expenseDTO);
    }

    @PostMapping("/amount/filter")
    public List<Expense> filterExpensesByAmount(@RequestBody int amount) {
        return expenseService.filterExpensesByAmount(amount);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateExpense(@Valid @RequestBody ExpenseDTO expense, @PathVariable Long id) {
        Expense toUpdateExpense = expenseService.updateExpense(id, expense);
        return ResponseEntity.ok("Expense updated: " + toUpdateExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
        if (expenseService.deleteById(id)) {
            return ResponseEntity.ok("Expense Was Deleted Successfully");
        }
        return ResponseEntity.internalServerError().build();
    }
}
