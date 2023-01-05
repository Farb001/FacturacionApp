package com.sc.utilities;

import com.sc.models.inventory.Product;

public class JSONProductFormat {

    private Product product;

    public JSONProductFormat(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
