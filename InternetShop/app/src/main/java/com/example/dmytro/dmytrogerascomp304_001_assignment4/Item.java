package com.example.dmytro.dmytrogerascomp304_001_assignment4;

public class Item {

    public Item() {
    }
    public int itemId;
    public String itemName;
    public double price;
    public String category;


    public Item(int itemId, String itemName, double price, String category) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.category = category;
    }



    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }






}
