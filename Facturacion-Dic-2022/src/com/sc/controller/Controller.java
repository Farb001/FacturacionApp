package com.sc.controller;

import com.sc.models.GeneralManager;
import com.sc.models.inventory.Product;
import com.sc.view.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Controller implements ActionListener {

    private GeneralManager manager;
    private MainFrame mainFrame;


    public Controller() {
        manager = new GeneralManager();
        mainFrame = new MainFrame(this, manager);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (Commands.valueOf(e.getActionCommand())) {
            case ADD_PRODUCT:
                mainFrame.showAddProductDialog();
                break;
            case EDIT_PRODUCT:
                showEditProduct();
                break;
            case DELETE_PRODUCT:
                deleteProduct();
                break;
            case ADD_BILLING:
                break;
            case EDIT_BILLING:
                break;
            case DELETE_BILLING:
                break;
            case ADD_PRODUCT_DIALOG:
                addProduct();
                break;
            case EDIT_PRODUCT_DIALOG:
                editProduct();
                break;
        }
    }

    private void deleteProduct() {
        try {
            manager.deleteProduct(mainFrame.deleteProduct());
            mainFrame.refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editProduct() {
        try {
            manager.editProduct(mainFrame.getCodeEditDialog(), mainFrame.getNameEditDialog(), mainFrame.getQuantityEditDialog(), mainFrame.getDescriptionEditDialog(), mainFrame.getFirstPriceEditDialog(), mainFrame.getSecondPriceEditDialog(), mainFrame.getThirdPriceEditDialog());
            mainFrame.refreshTable();
            mainFrame.closeEditProductDialog();
        } catch (IOException e) {
            mainFrame.showMessage(e.getMessage());
        }
    }

    private void showEditProduct() {
        Product aux = manager.getProduct(mainFrame.selectProduct());
        mainFrame.setDataEditDialog(aux.getCode(), aux.getName(), aux.getQuantity(), aux.getDescription(), aux.getFirstPrice(), aux.getSecondPrice(), aux.getThirdPrice());
        mainFrame.showEditProductDialog();
    }

    private void addProduct() {
        try {
            manager.addProduct(mainFrame.getCodeAddDialog(), mainFrame.getNameAddDialog(), mainFrame.getQuantityAddDialog(), mainFrame.getDescriptionAddDialog(), mainFrame.getFirstPriceAddDialog(), mainFrame.getSecondPriceAddDialog(), mainFrame.getThirdPriceAddDialog());
            mainFrame.refreshTable();
            mainFrame.closeAddDialog();

        } catch (IOException e) {
            mainFrame.showMessage(e.getMessage());
        }
    }

}
