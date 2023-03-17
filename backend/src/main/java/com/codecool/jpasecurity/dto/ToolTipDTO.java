package com.codecool.jpasecurity.dto;

public record ToolTipDTO(String month, long monthExpenseSum) {
    @Override
    public String toString() {
        return "ToolTipDTO{" +
                "month='" + month + '\'' +
                ", monthExpenseSum=" + monthExpenseSum +
                '}';
    }
}
