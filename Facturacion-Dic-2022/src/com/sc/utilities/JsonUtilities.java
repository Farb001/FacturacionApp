package com.sc.utilities;

import com.google.gson.*;
import com.sc.models.billing.Bill;
import com.sc.models.inventory.Product;


import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class JsonUtilities {

    private static final String CODE = "code";
    private static final String NAME = "name";
    private static final String QUANTITY = "quantity";
    private static final String DESCRIPTION = "description";
    private static final String FOLDER_PATH = "persistence";
    private static final String FILE_INVENTORY_PATH = "persistence/inventory.json";
    private static final String FILE_BILL_PATH = "persistence/billing.json";
    private static final String PRODUCT = "product";
    private static final String PRODUCTS = "products";
    private static final String BILL = "bill";
    private static final String FIRST_PRICE = "firstPrice";
    private static final String SECOND_PRICE = "secondPrice";
    private static final String THIRD_PRICE = "thirdPrice";

    public JsonUtilities() {
    }

    private void verifyFiles() throws IOException {
        File folder = new File(FOLDER_PATH);
        File writer = new File(FILE_INVENTORY_PATH);
        File billWrite = new File(FILE_BILL_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (Objects.requireNonNull(folder.list()).length < 2) {
            writer.createNewFile();
            billWrite.createNewFile();
        }
    }

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
                String name = String.valueOf(productJSON.get(NAME));
                int quantity = Integer.parseInt(String.valueOf(productJSON.get(QUANTITY)));
                String description = String.valueOf(productJSON.get(DESCRIPTION));
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
                String name = String.valueOf(productJSON.get(NAME));
                int quantity = Integer.parseInt(String.valueOf(productJSON.get(QUANTITY)));
                String description = String.valueOf(productJSON.get(DESCRIPTION));
                int firstPrice = Integer.parseInt(String.valueOf(productJSON.get(FIRST_PRICE)));
                int secondPrice = Integer.parseInt(String.valueOf(productJSON.get(SECOND_PRICE)));
                int thirdPrice = Integer.parseInt(String.valueOf(productJSON.get(THIRD_PRICE)));
                products.add(new Product(code, name, quantity, description, firstPrice, secondPrice, thirdPrice));
            }
        }
        return products;
    }

    public void writeNewInventoryData(int code, String name, int quantity, String description, int firstPrice, int secondPrice, int thirdPrice) throws IOException {
        String jsonList = new Gson().toJson(loadJSON()).replace("\"", "").replace("\\", "");
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
        String jsonList = new Gson().toJson(loadJSON()).replace("\"", "").replace("\\", "");
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
        String jsonList = new Gson().toJson(loadJSON()).replace("\"", "").replace("\\", "");
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
                ArrayList<Product> products = new Gson().fromJson(productsJSON, ArrayList.class);
                bills.add(new JSONBillFormat(new Bill(products, code)));
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
                ArrayList<Product> products = new Gson().fromJson(productsJSON, ArrayList.class);
                bills.add(new Bill(products, code));
            }
        }
        return bills;
    }

    public void writeNewBill(ArrayList<Product> products, int code) throws IOException {
        String jsonList = new Gson().toJson(loadBillJSON()).replace("\"", "").replace("\\", "");
        JsonArray jsonArray = JsonParser.parseString(jsonList).getAsJsonArray();
        if (!verifyBill(code)) {
            JsonObject billDetails = new JsonObject();
            JsonObject billObject = new JsonObject();
            billDetails.addProperty(CODE, code);
            billDetails.add(PRODUCTS, new Gson().toJsonTree(products));
            billObject.add(BILL, billDetails);
            jsonArray.add(billObject);
            try (Writer writer = new FileWriter(FILE_BILL_PATH)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(jsonArray, writer);
            }
        }
    }

    public void updateBill(ArrayList<Product> products, int code) throws IOException {
        String jsonList = new Gson().toJson(loadBillJSON()).replace("\"", "").replace("\\", "");
        JsonArray jsonArray = JsonParser.parseString(jsonList).getAsJsonArray();
        if (verifyBill(code)) {
            jsonArray.get(getProduct(code)).getAsJsonObject().get(BILL).getAsJsonObject().addProperty(CODE, code);
            jsonArray.get(getProduct(code)).getAsJsonObject().get(BILL).getAsJsonObject().add(PRODUCTS, new Gson().toJsonTree(products));
            try (Writer writer = new FileWriter(FILE_BILL_PATH)) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(jsonArray, writer);
            }
        }
    }

    public void deleteBill(int code) throws IOException {
        String jsonList = new Gson().toJson(loadBillJSON()).replace("\"", "").replace("\\", "");
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
}
