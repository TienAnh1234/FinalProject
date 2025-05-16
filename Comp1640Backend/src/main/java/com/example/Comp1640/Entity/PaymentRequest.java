package com.example.Comp1640.Entity;

public class PaymentRequest {

    private int amount;
    private String orderInfo;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public PaymentRequest(int amount, String orderInfo) {
        this.amount = amount;
        this.orderInfo = orderInfo;
    }

    public PaymentRequest() {}

}
