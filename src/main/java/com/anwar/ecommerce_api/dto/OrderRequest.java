package com.anwar.ecommerce_api.dto;

public class OrderRequest {

    private String shippingAddress;
    private String paymentMethod; // CARD, UPI, NET_BANKING

    private String status;

    // Getters and Setters
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
