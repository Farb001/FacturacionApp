package com.sc.models.billing;

import com.sc.models.inventory.Product;

import java.util.ArrayList;

public class Bill {

    public static final double TAX = 0.16;

    private int code;
    private ArrayList<BillProduct> products;
    private double total;
    private Client client;


    public Bill(ArrayList<BillProduct> products, double total, int code, Client client) {
        this.products = products;
        this.total = total;
        this.code = code;
        this.client = client;
    }

    public Bill(ArrayList<BillProduct> products, int code, Client client) {
        this.products = products;
        this.code = code;
        this.client = client;
    }

    public Bill() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<BillProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<BillProduct> products) {
        this.products = products;
    }

    public double getTotal() {
        double totalPrice = 0;
        for (int i = 0; i < products.size(); i++) {
            totalPrice += (products.get(i).getPrice() * products.get(i).getQuantity());
        }
        totalPrice += (totalPrice * TAX);
        return totalPrice;
    }

    public double getSubTotal(){
        double totalPrice = 0;
        for (int i = 0; i < products.size(); i++) {
            totalPrice += (products.get(i).getPrice() * products.get(i).getQuantity());
        }
        return totalPrice;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "code=" + code +
                ", products=" + products +
                ", total=" + total +
                ", client=" + client +
                '}';
    }
}
