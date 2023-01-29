package com.sc.utilities;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.sc.enums.DepartmentsEnum;
import com.sc.enums.PaymentTypeEnum;
import com.sc.models.Company;
import com.sc.models.billing.Bill;
import com.sc.models.billing.BillProduct;
import com.sc.models.billing.Client;
import com.sc.models.inventory.Product;


import java.io.*;
import java.util.ArrayList;

public class JsonUtilities {

    private static final String CODE = "code";
    private static final String NAME = "name";
    private static final String QUANTITY = "quantity";
    private static final String DESCRIPTION = "description";
    private static final String FOLDER_PATH = "persistence";
    private static final String FILE_INVENTORY_PATH = "persistence/inventory.json";
    private static final String FILE_BILL_PATH = "persistence/billing.json";
    private static final String FILE_COMPANY_PATH = "persistence/companies.json";
    private static final String FILE_CLIENTS_PATH = "persistence/clients.json";
    private static final String PRODUCT = "product";
    private static final String PRODUCTS = "products";
    private static final String BILL = "bill";
    private static final String FIRST_PRICE = "firstPrice";
    private static final String SECOND_PRICE = "secondPrice";
    private static final String THIRD_PRICE = "thirdPrice";
    private static final String TAX = "tax";
    private static final String TOTAL = "total";
    private static final String CLIENT = "client";
    private static final String PRICE = "price";
    private static final String COMPANY = "company";
    private static final String ADDRESS = "address";
    private static final String CITY = "city";
    private static final String DEPARTMENT = "department";
    private static final String PHONENUMBER = "phoneNumber";
    private static final String EMAIL = "email";
    private static final String IMG = "imgPath";
    private static final String HOUR = "billHour";
    private static final String DATE = "billDate";
    private static final String LASTNAME = "lastName";
    private static final String ID = "id";
    private static final String DISCOUNT = "discount";
    private static final String PAYMENTTYPE = "paymentType";

    public JsonUtilities() {
    }

    private void verifyFiles() throws IOException {
        File folder = new File(FOLDER_PATH);
        File inventoryWriter = new File(FILE_INVENTORY_PATH);
        File billWrite = new File(FILE_BILL_PATH);
        File companyWrite = new File(FILE_COMPANY_PATH);
        File clientWrite = new File(FILE_CLIENTS_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (!inventoryWriter.exists()) {
            inventoryWriter.createNewFile();
            FileWriter fileWriter = new FileWriter(inventoryWriter);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write("[]");
            writer.close();
            fileWriter.close();
        }
        if (!billWrite.exists()) {
            billWrite.createNewFile();
            FileWriter fileWriter = new FileWriter(billWrite);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write("[]");
            writer.close();
            fileWriter.close();
        }
        if (!companyWrite.exists()) {
            companyWrite.createNewFile();
            FileWriter fileWriter = new FileWriter(companyWrite);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write("[]");
            writer.close();
            fileWriter.close();
        }
        if (!clientWrite.exists()) {
            clientWrite.createNewFile();
            FileWriter fileWriter = new FileWriter(clientWrite);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write("[]");
            writer.close();
            fileWriter.close();
        }
    }

   // public String cleanString(String string) {
   //     return string.replace("_!", " ");
   // }

   // public String addSeparator(String string) {
   //     return string.replace(" ", "_!");
   // }

    public ArrayList<JSONProductFormat> loadJSON() throws IOException {
        verifyFiles();
        ArrayList<JSONProductFormat> products = new ArrayList<>();
        FileReader reader = new FileReader(FILE_INVENTORY_PATH);
        JsonElement object = JsonParser.parseReader(reader);
        if (object != null) {
            JsonArray listProducts = object.getAsJsonArray();
            for (int i = 0; i < listProducts.size(); i++) {
                JsonObject productJSON = (JsonObject) ((JsonObject) listProducts.get(i)).get(PRODUCT);
                int code = Integer.parseInt(String.valueOf(productJSON.get(CODE)));
                String name = String.valueOf(productJSON.get(NAME)).replace("\"","");
                int quantity = Integer.parseInt(String.valueOf(productJSON.get(QUANTITY)));
                String description = String.valueOf(productJSON.get(DESCRIPTION)).replace("\"","");
                int firstPrice = Integer.parseInt(String.valueOf(productJSON.get(FIRST_PRICE)));
                int secondPrice = Integer.parseInt(String.valueOf(productJSON.get(SECOND_PRICE)));
                int thirdPrice = Integer.parseInt(String.valueOf(productJSON.get(THIRD_PRICE)));
                products.add(new JSONProductFormat(new Product(code, name, quantity, description, firstPrice, secondPrice, thirdPrice)));
            }
        }
        return products;
    }

    public ArrayList<Product> loadProductJSON() throws IOException {
        verifyFiles();
        ArrayList<Product> products = new ArrayList<>();
        FileReader reader = new FileReader(FILE_INVENTORY_PATH);
        JsonElement object = JsonParser.parseReader(reader);
        if (object != null) {
            JsonArray listProducts = object.getAsJsonArray();
            for (int i = 0; i < listProducts.size(); i++) {
                JsonObject productJSON = (JsonObject) ((JsonObject) listProducts.get(i)).get(PRODUCT);
                int code = Integer.parseInt(String.valueOf(productJSON.get(CODE)));
                String name = String.valueOf(productJSON.get(NAME)).replace("\"", "");
                int quantity = Integer.parseInt(String.valueOf(productJSON.get(QUANTITY)));
                String description = String.valueOf(productJSON.get(DESCRIPTION)).replace("\"", "");
                int firstPrice = Integer.parseInt(String.valueOf(productJSON.get(FIRST_PRICE)));
                int secondPrice = Integer.parseInt(String.valueOf(productJSON.get(SECOND_PRICE)));
                int thirdPrice = Integer.parseInt(String.valueOf(productJSON.get(THIRD_PRICE)));
                products.add(new Product(code, name, quantity, description, firstPrice, secondPrice, thirdPrice));
            }
        }
        return products;
    }

    public void writeNewInventoryData(int code, String name, int quantity, String description, int firstPrice, int secondPrice, int thirdPrice) throws IOException {
        String jsonList = new Gson().toJson(loadJSON());
        System.out.println(jsonList);
        JsonArray jsonArray = JsonParser.parseString(jsonList).getAsJsonArray();
        if (!verifyProduct(code)) {
            JsonObject productDetails = new JsonObject();
            JsonObject productObject = new JsonObject();
            productDetails.addProperty(CODE, code);
            productDetails.addProperty(NAME, name);
            productDetails.addProperty(QUANTITY, quantity);
            productDetails.addProperty(DESCRIPTION, description);
            productDetails.addProperty(FIRST_PRICE, firstPrice);
            productDetails.addProperty(SECOND_PRICE, secondPrice);
            productDetails.addProperty(THIRD_PRICE, thirdPrice);
            productObject.add(PRODUCT, productDetails);
            jsonArray.add(productObject);
            try (Writer writer = new FileWriter(FILE_INVENTORY_PATH)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(jsonArray, writer);
            }
        }
    }

    public void updateInventoryData(int code, String name, int quantity, String description, int firstPrice, int secondPrice, int thirdPrice) throws IOException {
        String jsonList = new Gson().toJson(loadJSON()).replace("\"", "").replace("\\", "");
        JsonArray jsonArray = JsonParser.parseString(jsonList).getAsJsonArray();
        if (verifyProduct(code)) {
            jsonArray.get(getProduct(code)).getAsJsonObject().get(PRODUCT).getAsJsonObject().addProperty(CODE, code);
            jsonArray.get(getProduct(code)).getAsJsonObject().get(PRODUCT).getAsJsonObject().addProperty(NAME, name);
            jsonArray.get(getProduct(code)).getAsJsonObject().get(PRODUCT).getAsJsonObject().addProperty(QUANTITY, quantity);
            jsonArray.get(getProduct(code)).getAsJsonObject().get(PRODUCT).getAsJsonObject().addProperty(DESCRIPTION, description);
            jsonArray.get(getProduct(code)).getAsJsonObject().get(PRODUCT).getAsJsonObject().addProperty(FIRST_PRICE, firstPrice);
            jsonArray.get(getProduct(code)).getAsJsonObject().get(PRODUCT).getAsJsonObject().addProperty(SECOND_PRICE, secondPrice);
            jsonArray.get(getProduct(code)).getAsJsonObject().get(PRODUCT).getAsJsonObject().addProperty(THIRD_PRICE, thirdPrice);
            try (Writer writer = new FileWriter(FILE_INVENTORY_PATH)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(jsonArray, writer);
            }
        }
    }

    public void removeOneInventoryData(int code, int quantity) throws IOException {
        String jsonList = new Gson().toJson(loadJSON());
        JsonArray jsonArray = JsonParser.parseString(jsonList).getAsJsonArray();
        if (verifyProduct(code)) {
            jsonArray.get(getProduct(code)).getAsJsonObject().get(PRODUCT).getAsJsonObject().addProperty(QUANTITY, quantity);
            try (Writer writer = new FileWriter(FILE_INVENTORY_PATH)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(jsonArray, writer);
            }
        }
    }

    public void deleteProduct(int code) throws IOException {
        String jsonList = new Gson().toJson(loadJSON());
        JsonArray jsonArray = JsonParser.parseString(jsonList).getAsJsonArray();
        if (verifyProduct(code)) {
            jsonArray.remove(getProduct(code));
            try (Writer writer = new FileWriter(FILE_INVENTORY_PATH)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(jsonArray, writer);
            }
        }
    }

    private boolean verifyProduct(int code) throws FileNotFoundException {
        FileReader reader = new FileReader(FILE_INVENTORY_PATH);
        JsonElement object = JsonParser.parseReader(reader);
        if (object != null) {
            JsonArray listProducts = object.getAsJsonArray();
            for (int i = 0; i < listProducts.size(); i++) {
                JsonObject productJSON = (JsonObject) ((JsonObject) listProducts.get(i)).get(PRODUCT);
                if (Integer.parseInt(String.valueOf(productJSON.get(CODE))) == code) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verifyBill(int code) throws FileNotFoundException {
        FileReader reader = new FileReader(FILE_BILL_PATH);
        JsonElement object = JsonParser.parseReader(reader);
        if (object != null) {
            JsonArray listBills = object.getAsJsonArray();
            for (int i = 0; i < listBills.size(); i++) {
                JsonObject billJSON = (JsonObject) ((JsonObject) listBills.get(i)).get(BILL);
                if (Integer.parseInt(String.valueOf(billJSON.get(CODE))) == code) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getProduct(int code) throws FileNotFoundException {
        FileReader reader = new FileReader(FILE_INVENTORY_PATH);
        JsonElement object = JsonParser.parseReader(reader);
        int aux = 0;
        if (object != null) {
            JsonArray listProducts = object.getAsJsonArray();
            for (int i = 0; i < listProducts.size(); i++) {
                JsonObject productJSON = (JsonObject) ((JsonObject) listProducts.get(i)).get(PRODUCT);
                if (Integer.parseInt(String.valueOf(productJSON.get(CODE))) == code) {
                    aux = i;
                }
            }
        }
        return aux;
    }

    //Bill

    public ArrayList<JSONBillFormat> loadBillJSON() throws IOException {
        verifyFiles();
        ArrayList<JSONBillFormat> bills = new ArrayList<>();
        FileReader reader = new FileReader(FILE_BILL_PATH);
        JsonElement object = JsonParser.parseReader(reader);
        if (object != null) {
            JsonArray listProducts = object.getAsJsonArray();
            for (int i = 0; i < listProducts.size(); i++) {
                JsonObject billJSON = (JsonObject) ((JsonObject) listProducts.get(i)).get(BILL);
                int code = Integer.parseInt(String.valueOf(billJSON.get(CODE)));
                JsonArray productsJSON = (JsonArray) billJSON.get(PRODUCTS);
                ArrayList<LinkedTreeMap<String, Object>> productsTree = new Gson().fromJson(productsJSON, ArrayList.class);
                ArrayList<BillProduct> products = parseGsonTree(productsTree);
                Client client = new Gson().fromJson(billJSON.get(CLIENT), Client.class);
                String hour = String.valueOf(billJSON.get(HOUR)).replace("\"", "").toString();
                String date = String.valueOf(billJSON.get(DATE)).replace("\"", "").toString();
                double total = Double.parseDouble(String.valueOf(billJSON.get(TOTAL)));
                double discount = Double.parseDouble(String.valueOf(billJSON.get(DISCOUNT)));
                PaymentTypeEnum paymentType = PaymentTypeEnum.findPayment(String.valueOf(billJSON.get(PAYMENTTYPE)).replace("\"", ""));
                Bill bill = new Bill(products, code, client, hour, date, discount, paymentType);
                bill.setTotal(total);
                bill.setDiscount(discount);
                bill.setPaymentType(paymentType);
                bills.add(new JSONBillFormat(bill));
            }
        }
        return bills;
    }

    public ArrayList<Bill> loadBillArrayJSON() throws IOException {
        verifyFiles();
        ArrayList<Bill> bills = new ArrayList<>();
        FileReader reader = new FileReader(FILE_BILL_PATH);
        JsonElement object = JsonParser.parseReader(reader);
        if (object != null) {
            JsonArray listProducts = object.getAsJsonArray();
            for (int i = 0; i < listProducts.size(); i++) {
                JsonObject billJSON = (JsonObject) ((JsonObject) listProducts.get(i)).get(BILL);
                int code = Integer.parseInt(String.valueOf(billJSON.get(CODE)));
                JsonArray productsJSON = (JsonArray) billJSON.get(PRODUCTS);
                ArrayList<LinkedTreeMap<String, Object>> productsTree = new Gson().fromJson(productsJSON, ArrayList.class);
                ArrayList<BillProduct> products = parseGsonTree(productsTree);
                Client client = new Gson().fromJson(billJSON.get(CLIENT), Client.class);
                String hour = String.valueOf(billJSON.get(HOUR)).replace("\"", "").toString();
                String date = String.valueOf(billJSON.get(DATE)).replace("\"", "").toString();
                double total = Double.parseDouble(String.valueOf(billJSON.get(TOTAL)));
                double discount = Double.parseDouble(String.valueOf(billJSON.get(DISCOUNT)));
                PaymentTypeEnum paymentType = PaymentTypeEnum.findPayment(String.valueOf(billJSON.get(PAYMENTTYPE)).replace("\"", ""));
                Bill bill = new Bill(products, code, client, hour, date, discount, paymentType);
                bill.setTotal(total);
                bill.setDiscount(discount);
                bills.add(bill);
            }
        }
        return bills;
    }

    private ArrayList<BillProduct> parseGsonTree(ArrayList<LinkedTreeMap<String, Object>> productsTree) {
        ArrayList<BillProduct> products = new ArrayList<>();
        for (int j = 0; j < productsTree.size(); j++) {
            BillProduct product = new BillProduct();
            for (int k = 0; k < productsTree.get(j).size(); k++) {
                switch (k) {
                    case 0:
                        product.setCode((int) ((double) productsTree.get(j).get(CODE)));
                        break;
                    case 1:
                        product.setPrice((int) ((double) productsTree.get(j).get(PRICE)));
                        break;
                    case 2:
                        product.setName(String.valueOf(productsTree.get(j).get(NAME)));
                        break;
                    case 3:
                        product.setQuantity((int) ((double) productsTree.get(j).get(QUANTITY)));
                        break;
                    case 4:
                        product.setDescription(String.valueOf(productsTree.get(j).get(DESCRIPTION)));
                        break;
                }
            }
            products.add(product);
        }
        return products;
    }

    public void writeNewBill(Bill bill) throws IOException {
        String jsonList = new Gson().toJson(loadBillJSON());
        System.out.println(jsonList);
        JsonArray jsonArray = JsonParser.parseString(jsonList).getAsJsonArray();
        if (!verifyBill(bill.getCode())) {
            JsonObject billDetails = new JsonObject();
            JsonObject billObject = new JsonObject();
            billDetails.addProperty(CODE, bill.getCode());
            billDetails.add(CLIENT, new Gson().toJsonTree(bill.getClient()));
            billDetails.add(PRODUCTS, new Gson().toJsonTree(bill.getProducts(), ArrayList.class));
            billDetails.addProperty(TOTAL, bill.getTotal());
            billDetails.addProperty(DISCOUNT, bill.getDiscount());
            billDetails.addProperty(PAYMENTTYPE, bill.getPaymentType().name());
            billDetails.addProperty(HOUR, String.valueOf(bill.getBillHour()));
            billDetails.addProperty(DATE, String.valueOf(bill.getBillDate()));
            billObject.add(BILL, billDetails);
            jsonArray.add(billObject);
            try (Writer writer = new FileWriter(FILE_BILL_PATH)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(jsonArray, writer);
            }
        }
    }

    public void updateBill(ArrayList<BillProduct> products, int code, double discount) throws IOException {
        String jsonList = new Gson().toJson(loadBillJSON());
        JsonArray jsonArray = JsonParser.parseString(jsonList).getAsJsonArray();
        if (verifyBill(code)) {
            jsonArray.get(getProduct(code)).getAsJsonObject().get(BILL).getAsJsonObject().addProperty(CODE, code);
            jsonArray.get(getProduct(code)).getAsJsonObject().get(BILL).getAsJsonObject().add(PRODUCTS, new Gson().toJsonTree(products));
            jsonArray.get(getProduct(code)).getAsJsonObject().get(BILL).getAsJsonObject().addProperty(DISCOUNT, discount);
            try (Writer writer = new FileWriter(FILE_BILL_PATH)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(jsonArray, writer);
            }
        }
    }

    public void deleteBill(int code) throws IOException {
        String jsonList = new Gson().toJson(loadBillJSON());
        JsonArray jsonArray = JsonParser.parseString(jsonList).getAsJsonArray();
        if (verifyBill(code)) {
            jsonArray.remove(getBill(code));
            try (Writer writer = new FileWriter(FILE_BILL_PATH)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(jsonArray, writer);
            }
        }
    }

    private int getBill(int code) throws FileNotFoundException {
        FileReader reader = new FileReader(FILE_BILL_PATH);
        JsonElement object = JsonParser.parseReader(reader);
        int aux = 0;
        if (object != null) {
            JsonArray listBills = object.getAsJsonArray();
            for (int i = 0; i < listBills.size(); i++) {
                JsonObject billJSON = (JsonObject) ((JsonObject) listBills.get(i)).get(BILL);
                if (Integer.parseInt(String.valueOf(billJSON.get(CODE))) == code) {
                    aux = i;
                }
            }
        }
        return aux;
    }

    //Company
    public ArrayList<JSONCompanyFormat> loadCompanyJSON() throws IOException {
        verifyFiles();
        ArrayList<JSONCompanyFormat> companies = new ArrayList<>();
        FileReader reader = new FileReader(FILE_COMPANY_PATH);
        JsonElement object = JsonParser.parseReader(reader);
        if (object != null) {
            JsonArray listCompanies = object.getAsJsonArray();
            for (int i = 0; i < listCompanies.size(); i++) {
                JsonObject companyJSON = (JsonObject) ((JsonObject) listCompanies.get(i)).get(COMPANY);
                String name = String.valueOf(companyJSON.get(NAME)).replace("\"","");
                String address = String.valueOf(companyJSON.get(ADDRESS)).replace("\"","");
                String city = String.valueOf(companyJSON.get(CITY)).replace("\"","");
                DepartmentsEnum department = DepartmentsEnum.findDepartment(String.valueOf(companyJSON.get(DEPARTMENT)).replace("\"",""));
                String phoneNumber = String.valueOf(companyJSON.get(PHONENUMBER)).replace("\"","");
                String email = String.valueOf(companyJSON.get(EMAIL)).replace("\"","");
                String imgPath = String.valueOf(companyJSON.get(IMG)).replace("\"","");
                companies.add(new JSONCompanyFormat(new Company(name, address, city, department, phoneNumber, email, imgPath)));
            }
        }
        return companies;
    }

    public ArrayList<Company> loadCompanyArrayJSON() throws IOException {
        verifyFiles();
        ArrayList<Company> companies = new ArrayList<>();
        FileReader reader = new FileReader(FILE_COMPANY_PATH);
        JsonElement object = JsonParser.parseReader(reader);
        if (object != null) {
            JsonArray listCompanies = object.getAsJsonArray();
            for (int i = 0; i < listCompanies.size(); i++) {
                JsonObject companyJSON = (JsonObject) ((JsonObject) listCompanies.get(i)).get(COMPANY);
                String name = String.valueOf(companyJSON.get(NAME));
                String address = String.valueOf(companyJSON.get(ADDRESS));
                String city = String.valueOf(companyJSON.get(CITY));
                DepartmentsEnum department = DepartmentsEnum.findDepartment(String.valueOf(companyJSON.get(DEPARTMENT)));
                String phoneNumber = String.valueOf(companyJSON.get(PHONENUMBER));
                String email = String.valueOf(companyJSON.get(EMAIL));
                String imgPath = String.valueOf(companyJSON.get(IMG));
                companies.add(new Company(name, address, city, department, phoneNumber, email, imgPath));
            }
        }
        return companies;
    }

    public void writeNewCompany(Company company) throws IOException {
        String jsonList = new Gson().toJson(loadCompanyJSON());
        JsonArray jsonArray = JsonParser.parseString(jsonList).getAsJsonArray();
        if (!verifyCompany(company.getName())) {
            JsonObject companyDetails = new JsonObject();
            JsonObject companyObject = new JsonObject();
            companyDetails.addProperty(NAME, company.getName());
            companyDetails.addProperty(ADDRESS, company.getAddress());
            companyDetails.addProperty(CITY, company.getCity());
            companyDetails.addProperty(DEPARTMENT, company.getDepartment().name());
            companyDetails.addProperty(PHONENUMBER, company.getPhoneNumber());
            companyDetails.addProperty(EMAIL, company.getEmail());
            companyDetails.addProperty(IMG, company.getImgPath());
            companyObject.add(COMPANY, companyDetails);
            jsonArray.add(companyObject);
            try (Writer writer = new FileWriter(FILE_COMPANY_PATH)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(jsonArray, writer);
            }
        }
    }

    public void updateCompany(String name, String address, String city, DepartmentsEnum department, String phoneNumber, String email, String img) throws IOException {
        String jsonList = new Gson().toJson(loadCompanyJSON());
        JsonArray jsonArray = JsonParser.parseString(jsonList).getAsJsonArray();
        if (verifyCompany(name)) {
            jsonArray.get(getCompany(name)).getAsJsonObject().get(COMPANY).getAsJsonObject().addProperty(NAME, name);
            jsonArray.get(getCompany(name)).getAsJsonObject().get(COMPANY).getAsJsonObject().addProperty(ADDRESS, address);
            jsonArray.get(getCompany(name)).getAsJsonObject().get(COMPANY).getAsJsonObject().addProperty(CITY, city);
            jsonArray.get(getCompany(name)).getAsJsonObject().get(COMPANY).getAsJsonObject().addProperty(DEPARTMENT, department.name());
            jsonArray.get(getCompany(name)).getAsJsonObject().get(COMPANY).getAsJsonObject().addProperty(PHONENUMBER, phoneNumber);
            jsonArray.get(getCompany(name)).getAsJsonObject().get(COMPANY).getAsJsonObject().addProperty(EMAIL, email);
            jsonArray.get(getCompany(name)).getAsJsonObject().get(COMPANY).getAsJsonObject().addProperty(IMG, img);
            try (Writer writer = new FileWriter(FILE_COMPANY_PATH)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(jsonArray, writer);
            }
        }
    }

    public void updateImgCompany(String name, String img) throws IOException {
        String jsonList = new Gson().toJson(loadCompanyJSON());
        JsonArray jsonArray = JsonParser.parseString(jsonList).getAsJsonArray();
        if (verifyCompany(name)) {
            jsonArray.get(getCompany(name)).getAsJsonObject().get(COMPANY).getAsJsonObject().addProperty(IMG, img);
            try (Writer writer = new FileWriter(FILE_COMPANY_PATH)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(jsonArray, writer);
            }
        }
    }

    public void deleteCompany(String name) throws IOException {
        String jsonList = new Gson().toJson(loadCompanyJSON());
        JsonArray jsonArray = JsonParser.parseString(jsonList).getAsJsonArray();
        if (verifyCompany(name)) {
            jsonArray.remove(getCompany(name));
            try (Writer writer = new FileWriter(FILE_COMPANY_PATH)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(jsonArray, writer);
            }
        }
    }

    public boolean verifyCompany(String name) throws FileNotFoundException {
        FileReader reader = new FileReader(FILE_COMPANY_PATH);
        JsonElement object = JsonParser.parseReader(reader);
        if (object != null) {
            JsonArray listCompanies = object.getAsJsonArray();
            for (int i = 0; i < listCompanies.size(); i++) {
                JsonObject companyJSON = (JsonObject) ((JsonObject) listCompanies.get(i)).get(COMPANY);
                if (String.valueOf(companyJSON.get(NAME)).equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getCompany(String name) throws FileNotFoundException {
        FileReader reader = new FileReader(FILE_COMPANY_PATH);
        JsonElement object = JsonParser.parseReader(reader);
        int aux = 0;
        if (object != null) {
            JsonArray listCompanies = object.getAsJsonArray();
            for (int i = 0; i < listCompanies.size(); i++) {
                JsonObject companyJSON = (JsonObject) ((JsonObject) listCompanies.get(i)).get(COMPANY);
                if (String.valueOf(companyJSON.get(NAME)).equalsIgnoreCase(name)) {
                    aux = i;
                }
            }
        }
        return aux;
    }

    //Client

    public ArrayList<JSONClientFormat> loadClientJSON() throws IOException {
        verifyFiles();
        ArrayList<JSONClientFormat> clients = new ArrayList<>();
        FileReader reader = new FileReader(FILE_CLIENTS_PATH);
        JsonElement object = JsonParser.parseReader(reader);
        if (object != null) {
            JsonArray listClients = object.getAsJsonArray();
            for (int i = 0; i < listClients.size(); i++) {
                JsonObject clientJSON = (JsonObject) ((JsonObject) listClients.get(i)).get(CLIENT);
                long id = Long.parseLong(String.valueOf(clientJSON.get(ID)));
                String name = String.valueOf(clientJSON.get(NAME)).replace("\"","");
                String lastName = String.valueOf(clientJSON.get(LASTNAME)).replace("\"","");
                String address = String.valueOf(clientJSON.get(ADDRESS)).replace("\"","");
                String city = String.valueOf(clientJSON.get(CITY)).replace("\"","");
                DepartmentsEnum department = DepartmentsEnum.findDepartment(String.valueOf(clientJSON.get(DEPARTMENT)).replace("\"",""));
                String phoneNumber = String.valueOf(clientJSON.get(PHONENUMBER)).replace("\"","");
                String email = String.valueOf(clientJSON.get(EMAIL)).replace("\"","");
                clients.add(new JSONClientFormat(new Client(id, name, lastName, address, city, department, phoneNumber, email)));
            }
        }
        return clients;
    }

    public ArrayList<Client> loadClientArrayJSON() throws IOException {
        verifyFiles();
        ArrayList<Client> clients = new ArrayList<>();
        FileReader reader = new FileReader(FILE_CLIENTS_PATH);
        JsonElement object = JsonParser.parseReader(reader);
        if (object != null) {
            JsonArray listClients = object.getAsJsonArray();
            for (int i = 0; i < listClients.size(); i++) {
                JsonObject clientJSON = (JsonObject) ((JsonObject) listClients.get(i)).get(CLIENT);
                long id = Long.parseLong(String.valueOf(clientJSON.get(ID)));
                String name = String.valueOf(clientJSON.get(NAME));
                String lastName = String.valueOf(clientJSON.get(LASTNAME));
                String address = String.valueOf(clientJSON.get(ADDRESS));
                String city = String.valueOf(clientJSON.get(CITY));
                DepartmentsEnum department = DepartmentsEnum.findDepartment(String.valueOf(clientJSON.get(DEPARTMENT)));
                String phoneNumber = String.valueOf(clientJSON.get(PHONENUMBER));
                String email = String.valueOf(clientJSON.get(EMAIL));
                clients.add(new Client(id, name, lastName, address, city, department, phoneNumber, email));
            }
        }
        return clients;
    }

    public void writeNewClient(Client client) throws IOException {
        String jsonList = new Gson().toJson(loadClientJSON());
        JsonArray jsonArray = JsonParser.parseString(jsonList).getAsJsonArray();
        if (!verifyClient(client.getId())) {
            JsonObject clientDetails = new JsonObject();
            JsonObject clientObject = new JsonObject();
            clientDetails.addProperty(ID, client.getId());
            clientDetails.addProperty(NAME, client.getName());
            clientDetails.addProperty(LASTNAME, client.getLastName());
            clientDetails.addProperty(ADDRESS, client.getAddress());
            clientDetails.addProperty(CITY, client.getCity());
            clientDetails.addProperty(DEPARTMENT, client.getDepartment().name());
            clientDetails.addProperty(PHONENUMBER, client.getPhoneNumber());
            clientDetails.addProperty(EMAIL, client.getEmail());
            clientObject.add(CLIENT, clientDetails);
            jsonArray.add(clientObject);
            try (Writer writer = new FileWriter(FILE_CLIENTS_PATH)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(jsonArray, writer);
            }
        }
    }

    public void updateClient(Long id, String name, String lastName, String address, String city, DepartmentsEnum department, String phoneNumber, String email) throws IOException {
        String jsonList = new Gson().toJson(loadClientJSON());
        JsonArray jsonArray = JsonParser.parseString(jsonList).getAsJsonArray();
        if (verifyClient(id)) {
            jsonArray.get(getClient(id)).getAsJsonObject().get(CLIENT).getAsJsonObject().addProperty(NAME, name);
            jsonArray.get(getClient(id)).getAsJsonObject().get(CLIENT).getAsJsonObject().addProperty(LASTNAME, lastName);
            jsonArray.get(getClient(id)).getAsJsonObject().get(CLIENT).getAsJsonObject().addProperty(ADDRESS, address);
            jsonArray.get(getClient(id)).getAsJsonObject().get(CLIENT).getAsJsonObject().addProperty(CITY, city);
            jsonArray.get(getClient(id)).getAsJsonObject().get(CLIENT).getAsJsonObject().addProperty(DEPARTMENT, department.name());
            jsonArray.get(getClient(id)).getAsJsonObject().get(CLIENT).getAsJsonObject().addProperty(PHONENUMBER, phoneNumber);
            jsonArray.get(getClient(id)).getAsJsonObject().get(CLIENT).getAsJsonObject().addProperty(EMAIL, email);
            try (Writer writer = new FileWriter(FILE_CLIENTS_PATH)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(jsonArray, writer);
            }
        }
    }

    public void deleteClient(long id) throws IOException {
        String jsonList = new Gson().toJson(loadClientJSON());
        JsonArray jsonArray = JsonParser.parseString(jsonList).getAsJsonArray();
        if (verifyClient(id)) {
            jsonArray.remove(getClient(id));
            try (Writer writer = new FileWriter(FILE_CLIENTS_PATH)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(jsonArray, writer);
            }
        }
    }

    public boolean verifyClient(long id) throws FileNotFoundException {
        FileReader reader = new FileReader(FILE_CLIENTS_PATH);
        JsonElement object = JsonParser.parseReader(reader);
        if (object != null) {
            JsonArray listClients = object.getAsJsonArray();
            for (int i = 0; i < listClients.size(); i++) {
                JsonObject clientJSON = (JsonObject) ((JsonObject) listClients.get(i)).get(CLIENT);
                if (Long.parseLong(String.valueOf(clientJSON.get(ID))) == id) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getClient(Long id) throws FileNotFoundException {
        FileReader reader = new FileReader(FILE_CLIENTS_PATH);
        JsonElement object = JsonParser.parseReader(reader);
        int aux = 0;
        if (object != null) {
            JsonArray listClients = object.getAsJsonArray();
            for (int i = 0; i < listClients.size(); i++) {
                JsonObject clientJSON = (JsonObject) ((JsonObject) listClients.get(i)).get(CLIENT);
                if (Long.parseLong(String.valueOf(clientJSON.get(ID))) == id) {
                    aux = i;
                }
            }
        }
        return aux;
    }
}
