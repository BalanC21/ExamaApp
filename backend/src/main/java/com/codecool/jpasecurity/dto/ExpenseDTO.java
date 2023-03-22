package com.codecool.jpasecurity.dto;

import com.codecool.jpasecurity.enums.ExpenseType;
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
    @NotNull
    @Positive
    private Long amount;
    @NotBlank
    @Size(min = 2, max = 50)
    private String description;
    @NotNull
    private ExpenseType expenseType;
    @NotNull
    private LocalDate localDate;
}
