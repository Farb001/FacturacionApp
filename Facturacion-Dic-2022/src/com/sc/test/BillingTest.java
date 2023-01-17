package com.sc.test;

import com.sc.models.GeneralManager;
import com.sc.models.billing.Bill;
import com.sc.models.billing.BillProduct;
import com.sc.models.billing.BillingManager;
import com.sc.models.billing.Client;
import com.sc.models.inventory.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class BillingTest {

    private BillingManager billingManager;
    private ArrayList<BillProduct> products;
    private GeneralManager generalManager;

    @Before
    public void initManager() throws IOException {
        this.billingManager = new BillingManager();
        this.generalManager = new GeneralManager();
        this.products = new ArrayList<>();
    }

    @Test
    public void addBillTest(){
        billingManager.addBill(products, 12, new Client(1002693485, "Fabian", "Rodriguez"));
        Assert.assertTrue(billingManager.getBill(12).getProducts().get(0).getName().equalsIgnoreCase("Arroz"));
    }

    @Test
    public void editBillTest(){
        ArrayList<BillProduct> newProducts = new ArrayList<>();
        newProducts.add(new BillProduct(33, 3500, "Jabon", 4));
        billingManager.addBill(products, 12, new Client(1002693485, "Fabian", "Rodriguez"));
        billingManager.editBill(newProducts, 12);
        Assert.assertTrue(billingManager.getBill(12).getProducts().get(0).getName().equalsIgnoreCase("Jabon"));
    }

    @Test
    public void deleteBillTest(){
        billingManager.addBill(products, 12, new Client(1002693485, "Fabian", "Rodriguez"));
        billingManager.deleteBill(12);
        Assert.assertTrue(billingManager.getBill(12).getProducts() == null);
    }

    @Test
    public void writeJSON() {
        try {
            Product aux = generalManager.getProduct(1);
            Product aux2 = generalManager.getProduct(2);
            BillProduct product = new BillProduct(aux.getCode(), aux.getFirstPrice(), aux.getName().replace("\"", "").trim(), 3);
            BillProduct product2 = new BillProduct(aux2.getCode(), aux2.getFirstPrice(), aux2.getName().replace("\"", "").trim(), 2);
            products.add(product);
            products.add(product2);
            generalManager.addBill(products, 12, new Client(1002693485, "Fabian", "Rodriguez"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
