package com.example.Comp1640.DTO;

public class MonthlyTotalDto {
    private int month;
    private long totalAmount;

    public MonthlyTotalDto(int month, long totalAmount) {
        this.month = month;
        this.totalAmount = totalAmount;
    }

    public MonthlyTotalDto() {}

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

}
