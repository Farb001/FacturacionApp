package com.sc.models.billing;

public class BillProduct {

    public static final double TAX = 0.16;
    private int code;
    private int price;
    private String name;
    private int quantity;
    private String description;

    public BillProduct(int code, int price, String name, int quantity, String description) {
        this.code = code;
        this.price = price;
        this.name = name;
        this.quantity = quantity;
        setDescription(description);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTaxProduct(){
        return (int)(price * TAX);
    }

    public int getTaxTotalProducts(){
        return (int)((quantity * price) * TAX);
    }

    @Override
    public String toString() {
        return "BillProduct{" +
                "code=" + code +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                '}';
    }
}
