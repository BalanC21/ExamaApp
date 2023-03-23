package com.codecool.jpasecurity.dto;

import com.codecool.jpasecurity.enums.ExpenseType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExpenseDTO {
    @NotNull(message = "Amount should not be null!")
    @Positive(message = "Amount should be greater than 0!")
    private Long amount;
    @NotBlank
    @Size(min = 2, max = 50, message = "Description should be between 2 and 50 characters inclusive!")
    private String description;
    @NotNull(message = "Expense Type should not be null!")
    @Column(unique = true)
    private ExpenseType expenseType;
    @NotNull(message = "Local Date should not be null!")
    private LocalDate localDate;
}
