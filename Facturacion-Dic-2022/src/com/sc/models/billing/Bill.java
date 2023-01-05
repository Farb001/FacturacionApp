package com.sc.models.billing;

import com.sc.models.inventory.Product;

import java.util.ArrayList;

public class Bill {

    private static final double TAX = 0.16;

    private int code;
    private ArrayList<Product> products;
    private double total;
    private double tax;

    public Bill(ArrayList<Product> products, double total, int code) {
        this.products = products;
        this.total = total;
        this.code = code;
    }

    public Bill(ArrayList<Product> products, int code) {
        this.products = products;
        this.code = code;
    }

    public Bill(){}

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public double getTotal() {
        double totalPrice = 0;
        for (int i = 0; i < products.size(); i++) {
            totalPrice += products.get(i).getFirstPrice();
        }
        totalPrice += (totalPrice * TAX);
        return totalPrice;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }
}
