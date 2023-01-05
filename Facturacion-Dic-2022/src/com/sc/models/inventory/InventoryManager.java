package com.sc.models.inventory;

import java.util.ArrayList;

public class InventoryManager {

    private ArrayList<Product> products;

    public InventoryManager() {
        products = new ArrayList<>();
    }

    public void addProduct(int code, String name, int quantity, String description, int firstPrice, int secondPrice, int thirdPrice) {
        if (!products.isEmpty()) {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getCode() != code) {
                    products.add(new Product(code, name, quantity, description, firstPrice, secondPrice, thirdPrice));
                }
            }
        } else {
            products.add(new Product(code, name, quantity, description, firstPrice, secondPrice, thirdPrice));
        }
    }

    public void update(int code, String name, int quantity, String description, int firstPrice, int secondPrice, int thirdPrice) {
        for (Product product : products) {
            if (product.getCode() == code) {
                product.setName(name);
                product.setQuantity(quantity);
                product.setDescription(description);
                product.setFirstPrice(firstPrice);
                product.setSecondPrice(secondPrice);
                product.setThirdPrice(thirdPrice);
            }
        }
    }

    public void deleteProduct(int code) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getCode() == code) {
                products.remove(i);
            }
        }
    }

    public Product getProduct(int code) {
        Product product = new Product();
        for (Product value : products) {
            if (value.getCode() == code) {
                product = value;
            }
        }
        return product;
    }

    public void removeOneProduct(int code) {
        if (!products.isEmpty()) {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getCode() == code && products.get(i).getQuantity() > 0) {
                    products.get(i).setQuantity(products.get(i).getQuantity() - 1);
                }
            }
        }
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
