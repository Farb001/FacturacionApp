package com.sc.utilities;

import com.sc.models.billing.Bill;

public class JSONBillFormat {

    private Bill bill;

    public JSONBillFormat(Bill bill) {
        this.bill = bill;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
