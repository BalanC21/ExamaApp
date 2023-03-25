package com.codecool.jpasecurity.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LogInDTO(
        @NotBlank
        @Size(max = 20)
        @Column(unique = true)
        String username,
        @NotBlank
        @Size(min = 5, max = 150)
        String password
) {
}
