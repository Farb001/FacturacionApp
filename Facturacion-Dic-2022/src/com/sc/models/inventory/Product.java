package com.sc.models.inventory;


public class Product {

    private int code;
    private String name;
    private int quantity;
    private String description;
    private int firstPrice;
    private int secondPrice;
    private int thirdPrice;

    public Product(int code, String name, int quantity, String description) {
        this.code = code;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
    }

    public Product(int code, String name, int quantity, String description, int firstPrice, int secondPrice, int thirdPrice) {
        this.code = code;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.firstPrice = firstPrice;
        this.secondPrice = secondPrice;
        this.thirdPrice = thirdPrice;
    }


    public Product() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(int firstPrice) {
        this.firstPrice = firstPrice;
    }

    public int getSecondPrice() {
        return secondPrice;
    }

    public void setSecondPrice(int secondPrice) {
        this.secondPrice = secondPrice;
    }

    public int getThirdPrice() {
        return thirdPrice;
    }

    public void setThirdPrice(int thirdPrice) {
        this.thirdPrice = thirdPrice;
    }
}
