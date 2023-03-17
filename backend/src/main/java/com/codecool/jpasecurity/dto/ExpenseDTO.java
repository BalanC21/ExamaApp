package com.codecool.jpasecurity.dto;

import com.codecool.jpasecurity.enums.ExpenseType;

import java.time.LocalDateTime;

public record ExpenseDTO(LocalDateTime date, Long amount, ExpenseType expenseType, String description) {
}
