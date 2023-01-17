package com.sc.models.billing;

public class BillProduct {

    private int code;
    private int price;
    private String name;
    private int quantity;

    public BillProduct(int code, int price, String name, int quantity) {
        this.code = code;
        this.price = price;
        this.name = name;
        this.quantity = quantity;
    }

    public BillProduct() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    @Override
    public String toString() {
        return "BillProduct{" +
                "code=" + code +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
