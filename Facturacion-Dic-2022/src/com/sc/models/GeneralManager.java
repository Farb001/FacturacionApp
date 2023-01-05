package com.sc.models;

import com.sc.models.billing.Bill;
import com.sc.models.billing.BillingManager;
import com.sc.models.inventory.InventoryManager;
import com.sc.models.inventory.Product;
import com.sc.utilities.JsonUtilities;

import java.io.IOException;
import java.util.ArrayList;

public class GeneralManager {

    private final InventoryManager inventoryManager;
    private final BillingManager billingManager;
    private final JsonUtilities jsonUtilities;

    public GeneralManager() {
        this.inventoryManager = new InventoryManager();
        this.jsonUtilities = new JsonUtilities();
        this.billingManager = new BillingManager();
    }

    public void loadInfo() throws IOException {
        inventoryManager.setProducts(jsonUtilities.loadProductJSON());
        billingManager.setBills(jsonUtilities.loadBillArrayJSON());
    }

    //CRUD Products
    public void addProduct(int code, String name, int quantity, String description, int firstPrice, int secondPrice, int thirdPrice) throws IOException {
        inventoryManager.addProduct(code, name, quantity, description, firstPrice, secondPrice, thirdPrice);
        jsonUtilities.writeNewInventoryData(code, name, quantity, description, firstPrice, secondPrice, thirdPrice);
    }

    public void editProduct(int code, String name, int quantity, String description, int firstPrice, int secondPrice, int thirdPrice) throws IOException {
        inventoryManager.update(code, name, quantity, description, firstPrice, secondPrice, thirdPrice);
        jsonUtilities.updateInventoryData(code, name, quantity, description, firstPrice, secondPrice, thirdPrice);
    }

    public void deleteProduct(int code) throws IOException {
        inventoryManager.deleteProduct(code);
        jsonUtilities.deleteProduct(code);
    }

    public ArrayList<Product> getProducts() {
        try {
            loadInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < inventoryManager.getProducts().size(); i++) {
            inventoryManager.getProducts().get(i).setName(inventoryManager.getProducts().get(i).getName().replace("\"", ""));
            inventoryManager.getProducts().get(i).setDescription(inventoryManager.getProducts().get(i).getDescription().replace("\"", ""));
        }
        return inventoryManager.getProducts();
    }

    public Product getProduct(int code) {
        return inventoryManager.getProduct(code);
    }

    //CRUD Bills
    public void addBill(ArrayList<Product> products, int code) throws IOException {
        for (int i = 0; i < products.size(); i++) {
            inventoryManager.removeOneProduct(products.get(i).getCode());
            jsonUtilities.removeOneInventoryData(products.get(i).getCode(), inventoryManager.getProduct(products.get(i).getCode()).getQuantity());
        }
        billingManager.addBill(products, code);
        jsonUtilities.writeNewBill(products, code);
    }

    public void editBill(ArrayList<Product> products, int code) throws IOException {
        billingManager.editBill(products, code);
        jsonUtilities.updateBill(products, code);
    }

    public void deleteBill(int code) throws IOException {
        billingManager.deleteBill(code);
        jsonUtilities.deleteBill(code);
    }

    public ArrayList<Bill> getBills() {
        return billingManager.getBills();
    }

    public Bill getBill(int code) {
        return billingManager.getBill(code);
    }
}
