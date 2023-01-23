package com.sc.models.billing;

import java.util.ArrayList;

public class BillingManager {

    private ArrayList<Bill> bills;

    public BillingManager() {
        this.bills = new ArrayList<>();
    }

    public void addBill(ArrayList<BillProduct> products, int code, Client client) {
        if (!bills.isEmpty()) {
            for (int i = 0; i < bills.size(); i++) {
                if (bills.get(i).getCode() != code) {
                    Bill bill = new Bill(products, code, client);
                    bill.setTotal(bill.getTotal());
                    bills.add(bill);
                }
            }
        } else {
            Bill bill = new Bill(products, code, client);
            bill.setTotal(bill.getTotal());
            bills.add(bill);
        }
    }

    public void editBill(ArrayList<BillProduct> products, int code) {
        if (!bills.isEmpty()) {
            for (int i = 0; i < bills.size(); i++) {
                if (bills.get(i).getCode() == code) {
                    bills.get(i).setCode(code);
                    bills.get(i).setProducts(products);
                }
            }
        }
    }

    public void deleteBill(int code) {
        if (!bills.isEmpty()) {
            for (int i = 0; i < bills.size(); i++) {
                if (bills.get(i).getCode() == code) {
                    bills.remove(i);
                }
            }
        }
    }

    public Bill getBill(int code) {
        Bill bill = new Bill();
        if (!bills.isEmpty()) {
            for (int i = 0; i < bills.size(); i++) {
                if (code == bills.get(i).getCode()) {
                    bill = bills.get(i);
                }
            }
        }
        return bill;
    }

    public ArrayList<Bill> getBills() {
        return bills;
    }

    public void setBills(ArrayList<Bill> bills) {
        this.bills = bills;
    }
}
