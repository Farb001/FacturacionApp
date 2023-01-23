package com.sc.models;

import com.sc.models.billing.Bill;
import com.sc.models.billing.BillProduct;
import com.sc.models.billing.BillingManager;
import com.sc.models.billing.Client;
import com.sc.models.inventory.InventoryManager;
import com.sc.models.inventory.Product;
import com.sc.utilities.JsonUtilities;
import com.sc.utilities.PDFManager;

import java.io.IOException;
import java.util.ArrayList;

public class GeneralManager {

    private final InventoryManager inventoryManager;
    private final BillingManager billingManager;
    private final JsonUtilities jsonUtilities;
    private final PDFManager pdfManager;

    public GeneralManager() {
        this.inventoryManager = new InventoryManager();
        this.jsonUtilities = new JsonUtilities();
        this.billingManager = new BillingManager();
        this.pdfManager = new PDFManager();
    }

    public void loadInfo() throws IOException {
        inventoryManager.setProducts(jsonUtilities.loadProductJSON());
        billingManager.setBills(jsonUtilities.loadBillArrayJSON());
    }

    //CRUD Products
    public void addProduct(int code, String name, int quantity, String description, int firstPrice, int secondPrice, int thirdPrice) throws IOException {
        inventoryManager.addProduct(code, name, quantity, description, firstPrice, secondPrice, thirdPrice);
        jsonUtilities.writeNewInventoryData(code, name, quantity, description, firstPrice, secondPrice, thirdPrice);
        System.out.println("Escribio");
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

    public Product getProduct(int code) throws IOException {
        loadInfo();
        return inventoryManager.getProduct(code);
    }

    //CRUD Bills
    public void addBill(ArrayList<BillProduct> products, int code, Client client) throws Exception {
        for (int i = 0; i < products.size(); i++) {
            if (inventoryManager.getProduct(products.get(i).getCode()) != null && products.get(i).getQuantity() < inventoryManager.getProduct(products.get(i).getCode()).getQuantity()) {
                inventoryManager.removeOneProduct(products.get(i).getCode(), products.get(i).getQuantity());
                jsonUtilities.removeOneInventoryData(products.get(i).getCode(), inventoryManager.getProduct(products.get(i).getCode()).getQuantity());
            } else {
                throw new Exception("Existe un error con los productos");
            }
        }
        billingManager.addBill(products, code, client);
        jsonUtilities.writeNewBill(billingManager.getBill(code));
    }

    public void editBill(ArrayList<BillProduct> products, int code) throws IOException {
        billingManager.editBill(products, code);
        jsonUtilities.updateBill(products, code);
    }

    public void deleteBill(int code) throws IOException {
        billingManager.deleteBill(code);
        jsonUtilities.deleteBill(code);
    }

    public void setBillProducts(int billCode, int productCode, int quantity) {
        Product aux = inventoryManager.getProduct(productCode);
        BillProduct product = new BillProduct(aux.getCode(), aux.getFirstPrice(), aux.getName(), quantity);
        billingManager.getBill(billCode).getProducts().add(product);
    }

    public ArrayList<BillProduct> getBillProduct(int code) {
        return billingManager.getBill(code).getProducts();
    }

    public ArrayList<Bill> getBills() {
        return billingManager.getBills();
    }

    public Bill getBill(int code) {
        return billingManager.getBill(code);
    }

    public void generateTicket(Bill bill){
        pdfManager.generateTicket(bill);
    }

    public void generateDetailedTicket(Bill bill){
        pdfManager.generateDetailedTicket(bill);
    }
}
