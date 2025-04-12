package com.strechdstudio.app.dto;

import com.strechdstudio.app.model.Order;
import com.strechdstudio.app.util.DateTimeUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrderDTO {
    private UUID orderId;
    private int customerId;
    private String orderNumber;
    private String customerName;
    private BigDecimal totalAmount;
    private int statusId;
    private String status;

    private String addDate;
    private String addWho;
    private LocalDateTime editDate;
    private String editWho;

    public OrderDTO() {
    }

    public OrderDTO(Order order) {
        this.orderId = order.getOrderId();
        this.orderNumber = order.getOrderNumber();
        this.customerId = order.getCustomer().getCustomerId();
        this.customerName = order.getCustomer().getFullname();
        this.totalAmount = order.getTotalAmount();
        this.statusId = order.getStatus().getCodeLkupId();
        this.status = order.getStatus().getCode();
        this.addDate = DateTimeUtils.formatDateTime(order.getAddDate());
        this.addWho = order.getAddWho();
        this.editDate = order.getEditDate();
        this.editWho = order.getEditWho();
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
