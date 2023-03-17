package com.codecool.jpasecurity.dto;

import com.codecool.jpasecurity.enums.ExpenseType;

public record CatChart(ExpenseType name, int value) {
    @Override
    public String toString() {
        return "CatChart{" +
                "catName= " + name +
                ", catValue= " + value +
                '}';
    }
}
