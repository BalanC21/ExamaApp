package com.codecool.jpasecurity.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpDTO(
        @NotBlank
        @Size(max = 20)
        @Column(unique = true)
        String username,
        @NotBlank
        @Size(min = 5, max = 150)
        String password,
        @NotBlank
        @Size(max = 80)
        @Email
        @Column(unique = true)
        String email
) {
    @Override
    public String toString() {
        return "SignUpDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}