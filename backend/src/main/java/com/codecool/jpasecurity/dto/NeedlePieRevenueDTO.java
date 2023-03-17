package com.codecool.jpasecurity.dto;

public record NeedlePieRevenueDTO(long value, long needleValue) {
    @Override
    public String toString() {
        return "NeedlePieRevenueDTO{" +
                "value=" + value +
                ", needleValue=" + needleValue +
                '}';
    }
}
