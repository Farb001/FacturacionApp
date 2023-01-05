package com.sc.view;

import com.sc.models.GeneralManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    private ActionListener actionListener;
    private SimpleView simpleView;
    private GeneralManager manager;
    private MainJMenuBar mainJMenuBar;
    private ControlTable controlTable;
    private SpringLayout layout;
    private AddProductJDialog addProductJDialog;
    private EditProductJDialog editProductJDialog;
    private JLabel inventoryTxt;

    public MainFrame(ActionListener actionListener, GeneralManager manager) {
        this.actionListener = actionListener;
        layout = new SpringLayout();
        this.setLayout(layout);
        this.getContentPane().setBackground(Color.decode("#DCECFC"));
        setTitle(Constants.TITTLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setMinimumSize(new Dimension(Constants.WIDTH_FRAME, Constants.HEIGHT_FRAME));
        setResizable(false);
        setLocationRelativeTo(null);
        initComponents(actionListener, manager);
        setVisible(true);
    }

    public void initComponents(ActionListener actionListener, GeneralManager manager) {
        mainJMenuBar = new MainJMenuBar(actionListener);
        this.setJMenuBar(mainJMenuBar);

        this.setBackground(Color.black);
        inventoryTxt = addJLabel(inventoryTxt, Constants.INVENTORY_JMENU, 200, 50);
        layout.putConstraint(SpringLayout.WEST, inventoryTxt, 300, SpringLayout.NORTH, getContentPane());
        this.add(inventoryTxt, BorderLayout.NORTH);

        controlTable = new ControlTable(manager);
        layout.putConstraint(SpringLayout.WEST, controlTable, 130, SpringLayout.NORTH, getContentPane());
        layout.putConstraint(SpringLayout.NORTH, controlTable, 50, SpringLayout.NORTH, getContentPane());
        this.add(controlTable, BorderLayout.CENTER);

        addProductJDialog = new AddProductJDialog(actionListener);
        editProductJDialog = new EditProductJDialog(actionListener);
        simpleView = new SimpleView();
    }

    public JLabel addJLabel(JLabel jLabel, String tittle, int width, int height) {
        jLabel = new JLabel(tittle);
        jLabel.setFont(Constants.MAIN_FONT_FRONT);
        jLabel.setPreferredSize(new Dimension(width, height));
        return jLabel;
    }

    public void showAddProductDialog() {
        addProductJDialog.setVisible(true);
    }

    public void showEditProductDialog() {
        editProductJDialog.setVisible(true);
    }

    public void closeEditProductDialog() {
        editProductJDialog.clear();
        editProductJDialog.setVisible(false);
    }

    public ArrayList<Integer> readPrices() {
        return simpleView.readPrices(simpleView.readInt(Constants.READ_TOTAL));
    }

    public int getCodeAddDialog() {
        return Integer.parseInt(addProductJDialog.getCode());
    }

    public String getNameAddDialog() {
        return addProductJDialog.getName();
    }

    public int getQuantityAddDialog() {
        return Integer.parseInt(addProductJDialog.getQuantity());
    }

    public String getDescriptionAddDialog() {
        return addProductJDialog.getDescription();
    }

    public int getFirstPriceAddDialog(){
        return Integer.parseInt(addProductJDialog.getFirstPrice());
    }

    public int getSecondPriceAddDialog(){
        return Integer.parseInt(addProductJDialog.getSecondPrice());
    }

    public int getThirdPriceAddDialog(){
        return Integer.parseInt(addProductJDialog.getThirdPrice());
    }

    public void showMessage(String message) {
        simpleView.showMessage(message);
    }

    public void closeAddDialog() {
        addProductJDialog.clear();
        addProductJDialog.setVisible(false);
    }

    public void refreshTable() {
        controlTable.createTable();
        controlTable.repaint();
        this.repaint();
    }

    public int selectProduct() {
        return simpleView.readInt(Constants.READ_CODE_EDIT);
    }

    public int deleteProduct() {
        return simpleView.readInt(Constants.READ_CODE);
    }

    public void setDataEditDialog(int code, String name, int quantity, String description, int firstPrice, int secondPrice, int thirdPrice) {
        editProductJDialog.setCode(code);
        editProductJDialog.setName(name);
        editProductJDialog.setQuantity(quantity);
        editProductJDialog.setDescription(description);
        editProductJDialog.setFirstPrice(String.valueOf(firstPrice));
        editProductJDialog.setSecondPrice(String.valueOf(secondPrice));
        editProductJDialog.setThirdPrice(String.valueOf(thirdPrice));
    }

    public int getCodeEditDialog() {
        return Integer.parseInt(editProductJDialog.getCode());
    }

    public String getNameEditDialog() {
        return editProductJDialog.getName();
    }

    public int getQuantityEditDialog() {
        return Integer.parseInt(editProductJDialog.getQuantity());
    }

    public String getDescriptionEditDialog() {
        return editProductJDialog.getDescription();
    }

    public int getFirstPriceEditDialog() {
        return Integer.parseInt(editProductJDialog.getFirstPrice());
    }

    public int getSecondPriceEditDialog() {
        return Integer.parseInt(editProductJDialog.getSecondPrice());
    }

    public int getThirdPriceEditDialog() {
        return Integer.parseInt(editProductJDialog.getThirdPrice());
    }
}
