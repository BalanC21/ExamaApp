package com.codecool.jpasecurity.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;

public record RevenueDTO(
        @Positive
        Long amount,
        boolean isRecursive,
        @Max(28)
        @Positive
        int renewalDayOfMonth
) {
}
