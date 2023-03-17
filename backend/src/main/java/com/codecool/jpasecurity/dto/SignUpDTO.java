package com.codecool.jpasecurity.dto;

public record SignUpDTO(String username, String password, String email) {
    @Override
    public String toString() {
        return "SignUpDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
