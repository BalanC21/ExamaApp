package com.codecool.jpasecurity.repository;

import com.codecool.jpasecurity.enums.ExpenseType;
import com.codecool.jpasecurity.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByOwner_UserId(long userId);

    Optional<List<Expense>> findAllByAmount(Long amount);

    Optional<List<Expense>> findAllByExpenseType(ExpenseType expenseType);

    @Query(value = "select Expense.expense_type from Expense where Expense.user_id = ?1", nativeQuery = true)
    Set<ExpenseType> findExpenseTypeFromUser(long id);

    int countByExpenseTypeAndOwner_UserId(ExpenseType expenseType, long id);

    List<Expense> findExpensesByLocalDateAfterAndLocalDateBeforeAndOwner_UserId(
            LocalDate publicationTimeStart,
            LocalDate publicationTimeEnd, long id
    );
}
