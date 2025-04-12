package com.strechdstudio.app.dto;

import java.util.UUID;

public class CheckoutRequest {
    private UUID cartId;
    private int customerId;

    public CheckoutRequest() {
    }

    public UUID getCartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
