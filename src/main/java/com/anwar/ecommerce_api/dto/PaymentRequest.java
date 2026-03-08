package com.anwar.ecommerce_api.dto;

public class PaymentRequest {

    private Long orderId;
    private String paymentMethod;  // CARD, UPI, NET_BANKING
    private String transactionId;  // received from payment gateway

    // Getters and Setters
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
