package com.example.asus.ilmuonedata.Model;

public class Product {
    private String productId;
    private String image;
    private String description;
    private String price;
    private String name;
    private String quantity;

    public Product() {
    }

    public Product(String productId, String price, String image, String description, String name, String quantity) {
        this.productId = productId;
        this.price = price;
        this.image = image;
        this.description = description;
        this.name = name;
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
