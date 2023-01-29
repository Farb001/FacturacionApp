package com.sc.models.billing;

import com.sc.enums.PaymentTypeEnum;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Bill {


    private int code;
    private ArrayList<BillProduct> products;
    private double total;
    private Client client;
    private String billHour;
    private String billDate;
    private double discount;
    private PaymentTypeEnum paymentType;


    public Bill(ArrayList<BillProduct> products, int code, Client client, String billHour, String billDate, double discount, PaymentTypeEnum paymentType) {
        this.products = products;
        this.code = code;
        this.client = client;
        this.billHour = billHour;
        this.billDate = billDate;
        this.discount = discount;
        this.paymentType = paymentType;
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
        totalPrice -= (totalPrice * discount);
        return totalPrice;
    }

    public double getSubTotal(){
        double totalPrice = 0;
        for (int i = 0; i < products.size(); i++) {
            totalPrice += (products.get(i).getPrice() * products.get(i).getQuantity());
        }
        totalPrice -= (totalPrice * BillProduct.TAX);
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

    public String getBillHour() {
        return billHour;
    }

    public void setBillHour(String billHour) {
        this.billHour = billHour;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public PaymentTypeEnum getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeEnum paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "code=" + code +
                ", products=" + products +
                ", total=" + total +
                ", client=" + client +
                ", billHour='" + billHour + '\'' +
                ", billDate='" + billDate + '\'' +
                ", discount=" + discount +
                ", paymentType=" + paymentType +
                '}';
    }
}
