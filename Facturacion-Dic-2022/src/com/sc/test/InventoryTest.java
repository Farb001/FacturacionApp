package com.sc.test;

import com.sc.models.inventory.InventoryManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import org.junit.Assert;

public class InventoryTest {

    private InventoryManager manager;

    @Before
    public void initManager() {
        manager = new InventoryManager();
    }

    @Test
    public void testAddProduct() {
        manager.addProduct(1, "Jabon", 50, "Aseo general", 3200, 2500, 3500);
        Assert.assertTrue(manager.getProduct(1).getName().equalsIgnoreCase("Jabon"));
    }

    @Test
    public void testUpdateProduct() {
        manager.addProduct(1, "Jabon", 50, "Aseo general", 3200, 2500, 3500);
        manager.update(1, "Jabon de baño", 32, "Aseo",3200, 2500, 3500);
        Assert.assertTrue(manager.getProduct(1).getName().equalsIgnoreCase("Jabon de baño"));
    }

    @Test
    public void testDeleteProduct() {
        manager.addProduct(1, "Jabon", 50, "Aseo general", 3200, 2500, 3500);
        for (int i = 0; i < manager.getProducts().size(); i++) {
            if (manager.getProducts().get(i).getCode() == 1) {
                manager.deleteProduct(1);
                break;
            }
        }
        Assert.assertNull(manager.getProduct(1).getName());
    }
}
