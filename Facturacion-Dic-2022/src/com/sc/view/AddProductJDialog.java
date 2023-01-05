package com.sc.view;

import com.sc.controller.Commands;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddProductJDialog extends JDialog {

    private JLabel codeText, nameText, quantityText, descriptionText, firstPriceText, secondPriceText, thirdPriceText;
    private JTextField code, name, quantity, description, firstPrice, secondPrice, thirdPrice;
    private JButton add, addPrice;
    private ActionListener actionListener;
    private GridBagConstraints constraints;

    public AddProductJDialog(ActionListener actionListener) {
        this.actionListener = actionListener;
        this.constraints = new GridBagConstraints();
        setSize(new Dimension(Constants.WIDTH_DIALOG, Constants.HEIGHT_DIALOG));
        setTitle(Constants.ADD_PRODUCT_DIALOG_TITLE);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(Color.white);
        setLayout(new GridBagLayout());
        initComponents(actionListener);
        setVisible(false);
    }

    public void initComponents(ActionListener actionListener) {
        codeText = addJLabel(codeText, Constants.CODE_LBL, 0, 0, 30, 32);
        this.add(codeText, constraints);
        code = addJTextFiled(code, 0, 1, 50, 32);
        this.add(code, constraints);
        nameText = addJLabel(nameText, Constants.NAME_LBL, 0, 2, 150, 32);
        this.add(nameText, constraints);
        name = addJTextFiled(name, 0, 3, 150, 32);
        this.add(name, constraints);
        quantityText = addJLabel(quantityText, Constants.QUANTITY_LBL, 0, 4, 200, 32);
        this.add(quantityText, constraints);
        quantity = addJTextFiled(quantity, 0, 5, 200, 32);
        this.add(quantity, constraints);
        descriptionText = addJLabel(descriptionText, Constants.DESCRIPTION_LBL, 0, 6, 140, 32);
        this.add(descriptionText, constraints);
        description = addJTextFiled(description, 0, 7, 140, 32);
        this.add(description, constraints);
        firstPriceText = addJLabel(firstPriceText, Constants.FIRST_PRICE_TEXT, 0, 8, 140, 32);
        this.add(firstPriceText, constraints);
        firstPrice = addJTextFiled(firstPrice, 0, 9, 140, 32);
        this.add(firstPrice, constraints);
        secondPriceText = addJLabel(secondPriceText, Constants.SECOND_PRICE_TEXT, 0, 10, 140, 32);
        this.add(secondPriceText, constraints);
        secondPrice = addJTextFiled(secondPrice, 0, 11, 140, 32);
        this.add(secondPrice, constraints);
        thirdPriceText = addJLabel(thirdPriceText, Constants.THIRD_PRICE_TEXT, 0, 12, 140, 32);
        this.add(thirdPriceText, constraints);
        thirdPrice = addJTextFiled(thirdPrice, 0, 13, 140, 32);
        this.add(thirdPrice, constraints);
        add = addJButton(add, Constants.ADD_PRODUCT_BTN, Commands.ADD_PRODUCT_DIALOG.name(), actionListener, 0, 14, 50, 32);
        this.add(add, constraints);
    }

    public JTextField addJTextFiled(JTextField textField, int posX, int posY, int width, int height) {
        textField = new JTextField();
        textField.setFont(Constants.TEXT_FONT);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = posX;
        constraints.gridy = posY;
        textField.setPreferredSize(new Dimension(width, height));
        constraints.insets = new Insets(2, 2, 3, 2);
        return textField;
    }

    public JLabel addJLabel(JLabel jLabel, String tittle, int posX, int posY, int width, int height) {
        jLabel = new JLabel(tittle);
        jLabel.setFont(Constants.MAIN_FONT);
        constraints.gridx = posX;
        constraints.gridy = posY;
        jLabel.setPreferredSize(new Dimension(width, height));
        return jLabel;
    }

    public JButton addJButton(JButton jButton, String tittle, String actionCommand, ActionListener actionListener, int posX, int posY, int width, int height) {
        jButton = new JButton(tittle);
        jButton.setFont(Constants.MAIN_FONT);
        jButton.setBackground(Color.decode("#DCECFC"));
        jButton.setActionCommand(actionCommand);
        jButton.addActionListener(actionListener);
        constraints.gridx = posX;
        constraints.gridy = posY;
        jButton.setPreferredSize(new Dimension(width, height));
        return jButton;
    }

    public String getCode() {
        return code.getText();
    }

    @Override
    public String getName() {
        return name.getText();
    }

    public String getQuantity() {
        return quantity.getText();
    }

    public String getDescription() {
        return description.getText();
    }

    public String getFirstPrice() {
        return firstPrice.getText();
    }

    public String getSecondPrice() {
        return secondPrice.getText();
    }

    public String getThirdPrice() {
        return thirdPrice.getText();
    }

    public void clear() {
        code.setText("");
        name.setText("");
        quantity.setText("");
        description.setText("");
    }
}
