package com.sc.test;

import com.sc.models.GeneralManager;
import com.sc.models.billing.BillingManager;
import com.sc.models.inventory.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class BillingTest {

    private BillingManager billingManager;
    private ArrayList<Product> products;
    private GeneralManager generalManager;

    @Before
    public void initManager() throws IOException {
        this.billingManager = new BillingManager();
        this.generalManager = new GeneralManager();
        this.products = generalManager.getProducts();
    }

    @Test
    public void addBillTest(){
        billingManager.addBill(products, 12);
        Assert.assertTrue(billingManager.getBill(12).getProducts().get(0).getName().equalsIgnoreCase("Arroz"));
    }

    @Test
    public void editBillTest(){
        ArrayList<Product> newProducts = new ArrayList<>();
        newProducts.add(new Product(33, "Jabon", 2, "Aseo", 4000, 2500, 3700));
        billingManager.addBill(products, 12);
        billingManager.editBill(newProducts, 12);
        Assert.assertTrue(billingManager.getBill(12).getProducts().get(0).getName().equalsIgnoreCase("Jabon"));
    }

    @Test
    public void deleteBillTest(){
        billingManager.addBill(products, 12);
        billingManager.deleteBill(12);
        Assert.assertTrue(billingManager.getBill(12).getProducts() == null);
    }

    @Test
    public void writeJSON() {
        try {
            generalManager.addBill(products, 12);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
