package com.strechdstudio.app.dto;

import java.util.UUID;

public class AddToCartRequest {
    private int customerId;
    private UUID productId;
    private int quantity;

    // Getters and setters
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public UUID getProductId() {
        return productId;
    }
    public void setProductId(UUID productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
