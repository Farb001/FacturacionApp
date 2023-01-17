package com.sc.test;

import com.sc.models.GeneralManager;
import com.sc.models.inventory.InventoryManager;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;

public class InventoryTest {

    private GeneralManager manager;

    @Before
    public void initManager() {
        manager = new GeneralManager();
    }

    @Test
    public void testAddProduct() {
        try {
            manager.addProduct(1, "Arroz", 30, "Comida", 2500, 3000, 5000);
            manager.addProduct(2, "Jabon", 50, "Aseo general", 3200, 2500, 3500);
            Assert.assertTrue("Jabon".equalsIgnoreCase(manager.getProduct(2).getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateProduct() {
        try {
            manager.addProduct(1, "Jabon", 50, "Aseo general", 3200, 2500, 3500);
            manager.editProduct(1, "Jabon de baño", 32, "Aseo",3200, 2500, 3500);
            Assert.assertTrue(manager.getProduct(1).getName().equalsIgnoreCase("Jabon de baño"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDeleteProduct() {
        try {
            manager.addProduct(1, "Jabon", 50, "Aseo general", 3200, 2500, 3500);
            for (int i = 0; i < manager.getProducts().size(); i++) {
                if (manager.getProducts().get(i).getCode() == 1) {
                    manager.deleteProduct(1);
                    break;
                }
            }
            Assert.assertNull(manager.getProduct(1).getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
