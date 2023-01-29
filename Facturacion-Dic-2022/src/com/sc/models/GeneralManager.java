package com.sc.models;

import com.sc.enums.DepartmentsEnum;
import com.sc.enums.PaymentTypeEnum;
import com.sc.models.billing.Bill;
import com.sc.models.billing.BillProduct;
import com.sc.models.billing.BillingManager;
import com.sc.models.billing.Client;
import com.sc.models.inventory.InventoryManager;
import com.sc.models.inventory.Product;
import com.sc.utilities.JsonUtilities;
import com.sc.utilities.PDFManager;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class GeneralManager {

    private final InventoryManager inventoryManager;
    private final BillingManager billingManager;
    private final JsonUtilities jsonUtilities;
    private final PDFManager pdfManager;
    private ArrayList<Company> companies;
    private ArrayList<Client> clients;
    private Company actualCompany;

    public GeneralManager() {
        this.inventoryManager = new InventoryManager();
        this.jsonUtilities = new JsonUtilities();
        this.billingManager = new BillingManager();
        this.pdfManager = new PDFManager();
        this.clients = new ArrayList<>();
        this.companies = new ArrayList<>();
    }

    public void initCompany(Company company) {
        actualCompany = company;
    }

    public void loadInfo() throws IOException {
        inventoryManager.setProducts(jsonUtilities.loadProductJSON());
        billingManager.setBills(jsonUtilities.loadBillArrayJSON());
        companies = jsonUtilities.loadCompanyArrayJSON();
        clients = jsonUtilities.loadClientArrayJSON();
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

    public Product getProduct(int code) throws IOException {
        loadInfo();
        return inventoryManager.getProduct(code);
    }

    //CRUD Bills
    public void addBill(ArrayList<BillProduct> products, int code, Client client, String billHour, String billDate, double discount, PaymentTypeEnum paymentType) throws Exception {
        for (int i = 0; i < products.size(); i++) {
            if (inventoryManager.getProduct(products.get(i).getCode()) != null && products.get(i).getQuantity() < inventoryManager.getProduct(products.get(i).getCode()).getQuantity()) {
                inventoryManager.removeOneProduct(products.get(i).getCode(), products.get(i).getQuantity());
                jsonUtilities.removeOneInventoryData(products.get(i).getCode(), inventoryManager.getProduct(products.get(i).getCode()).getQuantity());
            } else {
                throw new Exception("Existe un error con los productos");
            }
        }
        if (client != null) {
            if (verifyClient(client)){
                billingManager.addBill(products, code, client, billHour, billDate, discount, paymentType);
                jsonUtilities.writeNewBill(billingManager.getBill(code));
            }else{
                addClient(client.getId(), client.getName(), client.getLastName(), client.getAddress(), client.getCity(), client.getDepartment(), client.getPhoneNumber(), client.getEmail());
                billingManager.addBill(products, code, client, billHour, billDate, discount, paymentType);
                jsonUtilities.writeNewBill(billingManager.getBill(code));
            }
        }
        billingManager.addBill(products, code, client, billHour, billDate, discount, paymentType);
        jsonUtilities.writeNewBill(billingManager.getBill(code));
    }

    public void editBill(ArrayList<BillProduct> products, int code, double discount) throws IOException {
        billingManager.editBill(products, code, discount);
        jsonUtilities.updateBill(products, code, discount);
    }

    public void deleteBill(int code) throws IOException {
        billingManager.deleteBill(code);
        jsonUtilities.deleteBill(code);
    }

    public void setBillProducts(int billCode, int productCode, int quantity, String description) {
        Product aux = inventoryManager.getProduct(productCode);
        BillProduct product = new BillProduct(aux.getCode(), aux.getFirstPrice(), aux.getName(), quantity, description);
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

    public void generateTicket(Bill bill, double pay) {
        pdfManager.generateTicket(bill, pay);
    }

    public void generateDetailedTicket(Bill bill, Company company, String notes) {
        pdfManager.generateDetailedTicket(bill, company, notes);
    }

    //Add Companies
    public void addCompany(String name, String address, String city, DepartmentsEnum department, String phoneNumber, String email) {
        companies.add(new Company(name, address, city, department, phoneNumber, email));
    }

    public void addCompanyImg(String name, String address, String city, DepartmentsEnum department, String phoneNumber, String email, String img) throws IOException {
        companies.add(new Company(name, address, city, department, phoneNumber, email, img));
        jsonUtilities.writeNewCompany(new Company(name, address, city, department, phoneNumber, email, img));
    }

    public void editCompany(String name, String address, String city, DepartmentsEnum department, String phoneNumber, String email, String img) throws IOException {
        if (!companies.isEmpty()) {
            for (int i = 0; i < companies.size(); i++) {
                if (companies.get(i).getName().equalsIgnoreCase(name)) {
                    companies.get(i).setAddress(address);
                    companies.get(i).setCity(city);
                    companies.get(i).setDepartment(department);
                    companies.get(i).setPhoneNumber(phoneNumber);
                    companies.get(i).setEmail(email);
                    jsonUtilities.updateCompany(name, address, city, department, phoneNumber, email, img);
                }
            }
        }
    }

    public void deleteCompany(String name) throws IOException {
        if (!companies.isEmpty()) {
            for (int i = 0; i < companies.size(); i++) {
                if (companies.get(i).getName().equalsIgnoreCase(name)) {
                    companies.remove(i);
                    jsonUtilities.deleteCompany(name);
                }
            }
        }
    }

    public void updateCompanyImg(String name, String newPath) throws IOException {
        if (!companies.isEmpty()) {
            for (int i = 0; i < companies.size(); i++) {
                if (companies.get(i).getName().equalsIgnoreCase(name)) {
                    companies.get(i).setImgPath(newPath);
                    jsonUtilities.updateImgCompany(name, newPath);
                }
            }
        }
    }

    //CRUD Clients

    public void addClient(long id, String name, String lastName, String address, String city, DepartmentsEnum department, String phoneNumber, String email) throws IOException {
        clients.add(new Client(id, name, lastName, address, city, department, phoneNumber, email));
        jsonUtilities.writeNewClient(new Client(id, name, lastName, address, city, department, phoneNumber, email));
    }

    public void editClient(long id, String name, String lastName, String address, String city, DepartmentsEnum department, String phoneNumber, String email) throws IOException {
        if (!clients.isEmpty()) {
            for (int i = 0; i < clients.size(); i++) {
                if (clients.get(i).getId() == id) {
                    clients.get(i).setName(name);
                    clients.get(i).setLastName(lastName);
                    clients.get(i).setAddress(address);
                    clients.get(i).setCity(city);
                    clients.get(i).setDepartment(department);
                    clients.get(i).setPhoneNumber(phoneNumber);
                    clients.get(i).setEmail(email);
                    jsonUtilities.updateClient(id, name, lastName, address, city, department, phoneNumber, email);
                }
            }
        }
    }

    public void deleteClient(long id) throws IOException {
        if (!clients.isEmpty()) {
            for (int i = 0; i < clients.size(); i++) {
                if (clients.get(i).getId() == id) {
                    clients.remove(i);
                    jsonUtilities.deleteClient(id);
                }
            }
        }
    }

    public boolean verifyClient(Client client) {
        for (int i = 0; i < clients.size(); i++) {
            if (client.getId() == clients.get(i).getId()) {
                return true;
            }
        }
        return false;
    }

    public Company getActualCompany() {
        return actualCompany;
    }

    public void setActualCompany(Company actualCompany) {
        this.actualCompany = actualCompany;
    }
}
