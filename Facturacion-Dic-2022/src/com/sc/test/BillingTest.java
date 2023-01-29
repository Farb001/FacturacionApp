package com.sc.test;

import com.sc.enums.DepartmentsEnum;
import com.sc.enums.PaymentTypeEnum;
import com.sc.models.Company;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BillingTest {

    private BillingManager billingManager;
    private ArrayList<BillProduct> products;
    private GeneralManager generalManager;
    private Company company;

    @Before
    public void initManager() throws IOException {
        company = new Company("Supermercado","Avenida 9 #8-15", "Tunja", DepartmentsEnum.BOYACA, "3158716534", "supermercado@gmail.com");
        this.billingManager = new BillingManager();
        this.generalManager = new GeneralManager();
        this.generalManager.initCompany(company);
        this.generalManager.loadInfo();
        this.products = new ArrayList<>();
    }

    @Test
    public void addBillTest() throws Exception {
        Product aux = generalManager.getProduct(1);
        Product aux2 = generalManager.getProduct(2);
        BillProduct product = new BillProduct(aux.getCode(), aux.getFirstPrice(), aux.getName().replace("\"", "").trim(), 3, aux.getDescription().replace("\"", ""));
        BillProduct product2 = new BillProduct(aux2.getCode(), aux2.getFirstPrice(), aux2.getName().replace("\"", "").trim(), 2, aux2.getDescription().replace("\"", ""));
        Client client = new Client(1002693485, "Fabian", "Rodriguez", "Carrera 7a #7-86", "Duitama", DepartmentsEnum.BOYACA, "3203316477", "fabian25buitrago@gmail.com");
        products.add(product);
        products.add(product2);
        String hour = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        generalManager.addBill(products, 12, client, hour, String.valueOf(LocalDate.now()), 0, PaymentTypeEnum.CASH);
        Assert.assertTrue(generalManager.getBill(12).getProducts().get(0).getName().equalsIgnoreCase("Arroz"));
    }

    @Test
    public void addBillTest2() throws Exception {
        Product aux = generalManager.getProduct(2);
        BillProduct product = new BillProduct(aux.getCode(), aux.getFirstPrice(), aux.getName().replace("\"", "").trim(), 3, aux.getDescription());
        Client client = new Client(1002693486, "Andrey", "Buitrago", "Carrera 7a #7-86", "Duitama", DepartmentsEnum.BOYACA, "3203316477", "A_buitrago@gmail.com");
        products.add(product);
        String hour = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        generalManager.addBill(products, 14, client, hour, String.valueOf(LocalDate.now()), 0.05,PaymentTypeEnum.CARD);
        Assert.assertTrue(generalManager.getBill(14).getProducts().get(0).getName().equalsIgnoreCase("Jabon"));
    }

    @Test
    public void addBillTestWithoutClient() throws Exception {
        Product aux = generalManager.getProduct(1);
        Product aux2 = generalManager.getProduct(2);
        BillProduct product = new BillProduct(aux.getCode(), aux.getFirstPrice(), aux.getName().replace("\"", "").trim(), 1, aux.getDescription());
        BillProduct product2 = new BillProduct(aux2.getCode(), aux2.getFirstPrice(), aux2.getName().replace("\"", "").trim(), 2, aux2.getDescription());
        products.add(product);
        products.add(product2);
        String hour = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        generalManager.addBill(products, 13, null, hour, String.valueOf(LocalDate.now()), 0.05,PaymentTypeEnum.CASH);
        Assert.assertTrue(generalManager.getBill(13).getProducts().get(0).getName().equalsIgnoreCase("Arroz"));
    }

    @Test
    public void editBillTest() {
        ArrayList<BillProduct> newProducts = new ArrayList<>();
        newProducts.add(new BillProduct(33, 3500, "Jabon", 4, "Aseo general"));
        Client client = new Client(1002693485, "Fabian", "Rodriguez", "Carrera 7a #7-86", "Duitama", DepartmentsEnum.BOYACA, "3203316477", "fabian25buitrago@gmail.com");
        billingManager.addBill(products, 12, client, String.valueOf(LocalTime.now()), String.valueOf(LocalDate.now()), 0.05,PaymentTypeEnum.CASH);
        billingManager.editBill(newProducts, 12, 0.05);
        Assert.assertTrue(billingManager.getBill(12).getProducts().get(0).getName().equalsIgnoreCase("Jabon"));
    }

    @Test
    public void deleteBillTest() {
        Client client = new Client(1002693485, "Fabian", "Rodriguez", "Carrera 7a #7-86", "Duitama", DepartmentsEnum.BOYACA, "3203316477", "fabian25buitrago@gmail.com");
        billingManager.addBill(products, 12, client, String.valueOf(LocalTime.now()), String.valueOf(LocalDate.now()), 0.05, PaymentTypeEnum.CASH);
        billingManager.deleteBill(12);
        Assert.assertTrue(billingManager.getBill(12).getProducts() == null);
    }

    @Test
    public void generatePDF() {
        try {
            generalManager.loadInfo();
            Bill bill = generalManager.getBill(14);
            generalManager.generateTicket(bill, 25000);
            generalManager.generateDetailedTicket(bill, generalManager.getActualCompany(), "Ejemplo de las notas");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void different() {


    }
}
