package com.example.asus.ilmuonedata.Model;

public class Order {
    private String orderId;
    private String description;
    private String price;
    private String name;

    public Order() {
    }

    public Order(String orderId, String price, String description, String name) {
        this.orderId = orderId;
        this.price = price;
        this.description = description;
        this.name = name;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
