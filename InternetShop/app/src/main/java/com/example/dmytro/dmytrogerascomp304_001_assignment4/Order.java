package com.example.dmytro.dmytrogerascomp304_001_assignment4;

import java.util.Date;

public class Order {
    public Order() {
    }
    public int orderId;
    public String itemID;
    public String itemName;
    public double amount;
    public String DeliveryDate;
    public String status;
    public int customerId;

    public Order(int orderId, String itemID, String itemName, double amount, String deliveryDate, String status, int customerId) {
        this.orderId = orderId;
        this.itemID = itemID;
        this.itemName = itemName;
        this.amount = amount;
        DeliveryDate = deliveryDate;
        this.status = status;
        this.customerId = customerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
