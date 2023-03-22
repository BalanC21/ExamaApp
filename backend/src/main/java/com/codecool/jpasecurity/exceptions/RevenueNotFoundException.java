package com.codecool.jpasecurity.exceptions;

public class RevenueNotFoundException extends RuntimeException {
    public RevenueNotFoundException() {
        super("Revenue Not Found");
    }
}
