package com.codecool.jpasecurity.service;

import com.codecool.jpasecurity.dto.CatChart;
import com.codecool.jpasecurity.dto.NeedlePieRevenueDTO;
import com.codecool.jpasecurity.dto.ToolTipDTO;
import com.codecool.jpasecurity.enums.ExpenseType;
import com.codecool.jpasecurity.exceptions.RevenueNotFoundException;
import com.codecool.jpasecurity.model.Expense;
import com.codecool.jpasecurity.model.Revenue;
import com.codecool.jpasecurity.model.User;
import com.codecool.jpasecurity.repository.ExpenseRepository;
import com.codecool.jpasecurity.repository.RevenueRepository;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ChartService {
    private final ExpenseRepository expenseRepository;
    private final RevenueRepository revenueRepository;
    private final GetUsers getUsers;

    public ChartService(ExpenseRepository expenseRepository, RevenueRepository revenueRepository, GetUsers getUsers) {
        this.expenseRepository = expenseRepository;
        this.revenueRepository = revenueRepository;
        this.getUsers = getUsers;
    }

    private static long getSumByMonth(List<Expense> expenses, Month month) {
        return expenses.stream().
                filter(expense -> expense.getLocalDate()
                        .getMonth().equals(month)).mapToLong(Expense::getAmount).sum();
    }

    public List<CatChart> getPieChartDataForUser() {
        User owner = getUsers.getUser();
        return getCatChartsForUser(owner.userId);
    }

    public List<ToolTipDTO> getToolTipData() {

        List<Expense> expenses = getExpenseForOwner();

        return getToolTipSumByMonth(expenses);
    }

    private List<ToolTipDTO> getToolTipSumByMonth(List<Expense> expenses) {
        List<ToolTipDTO> toolTipDTOS = new ArrayList<>();

        Arrays.stream(getMonths()).forEach(month -> {
            long sumByMonth = getSumByMonth(expenses, month);
            if (sumByMonth != 0) {
                toolTipDTOS.add(new ToolTipDTO(month.name(), sumByMonth));
            }
        });

        return toolTipDTOS;
    }

    private List<Expense> getExpenseForOwner() {
        User owner = getUsers.getUser();
        return expenseRepository.findAllByOwner_UserId(owner.userId);
    }

    public List<NeedlePieRevenueDTO> getNeedlePieRevenueDTO() {
        List<Expense> expenses = getAllExpensesByUser();

        long expensesSum = getExpensesAmountSum(expenses);

        return getNeedlePieRevenueDTOS(expensesSum);
    }

    private List<CatChart> getCatChartsForUser(long userId) {
        List<CatChart> usersDataCatChart = new ArrayList<>();
        Set<ExpenseType> usersExpensesCategories = findExpenseTypeByUserId(userId);

        for (ExpenseType usersExpensesCategory : usersExpensesCategories) {
            CatChart catChart = toCatChart(usersExpensesCategory, userId);
            usersDataCatChart.add(catChart);
        }
        return usersDataCatChart;
    }

    private Set<ExpenseType> findExpenseTypeByUserId(long userId) {
        return expenseRepository.findExpenseTypeFromUser(userId);
    }

    private CatChart toCatChart(ExpenseType expenseType, long ownerId) {
        int numberOfExpenses = expenseRepository.countByExpenseTypeAndOwner_UserId(expenseType, ownerId);
        return new CatChart(expenseType, numberOfExpenses);
    }

    private List<NeedlePieRevenueDTO> getNeedlePieRevenueDTOS(long expensesSum) {
        List<NeedlePieRevenueDTO> needlePieRevenueDTOS = new ArrayList<>();
        Revenue revenue = getRevenueForUser();

        needlePieRevenueDTOS.add(new NeedlePieRevenueDTO(revenue.getAmount(), expensesSum));

        return needlePieRevenueDTOS;
    }

    private List<Expense> getAllExpensesByUser() {
        User owner = getUsers.getUser();
        return expenseRepository.findAllByOwner_UserId(owner.userId);
    }

    private Revenue getRevenueForUser() {
        User owner = getUsers.getUser();
        Optional<Revenue> ownerRevenue = revenueRepository.findByOwner_Username(owner.getUsername());

        if (ownerRevenue.isEmpty()) {
            throw new RevenueNotFoundException();
        }

        return ownerRevenue.get();
    }

    private long getExpensesAmountSum(List<Expense> expenses) {
        return expenses.stream().
                mapToLong(Expense::getAmount).sum();
    }

    private Month[] getMonths() {
        return Month.values();
    }
}
