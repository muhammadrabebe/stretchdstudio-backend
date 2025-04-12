package com.strechdstudio.app.dto;

import com.strechdstudio.app.model.Cart;

import java.time.LocalDateTime;
import java.util.UUID;

public class CartDTO {
    private UUID cartId;
    private int customerId;
    private String customerName;
    private int statusId;
    private String status;

    private LocalDateTime addDate;
    private String addWho;
    private LocalDateTime editDate;
    private String editWho;

    public CartDTO() {
    }

    public CartDTO(Cart cart) {
        this.cartId = cart.getCartId();
        this.customerId = cart.getCustomer().getCustomerId();
        this.customerName = cart.getCustomer().getFullname();
        this.statusId = cart.getStatus().getCodeLkupId();
        this.status = cart.getStatus().getCode();
        this.addDate = cart.getAddDate();
        this.addWho = cart.getAddWho();
        this.editDate = cart.getEditDate();
        this.editWho = cart.getEditWho();
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }

    public String getAddWho() {
        return addWho;
    }

    public void setAddWho(String addWho) {
        this.addWho = addWho;
    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }

    public String getEditWho() {
        return editWho;
    }

    public void setEditWho(String editWho) {
        this.editWho = editWho;
    }
}
