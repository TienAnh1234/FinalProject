package com.example.Comp1640.Entity;

public class PaymentReturn {

    private String orderInfo;
    private String paymentTime;
    private String transactionId;
    private String totalPrice;


    public PaymentReturn(String orderInfo, String paymentTime, String transactionId, String totalPrice) {
        this.orderInfo = orderInfo;
        this.paymentTime = paymentTime;
        this.transactionId = transactionId;
        this.totalPrice = totalPrice;
    }

    public PaymentReturn() {}

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

}
