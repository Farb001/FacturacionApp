package com.sc.view;

import com.sc.models.GeneralManager;
import com.sc.models.inventory.Product;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;


public class ControlTable extends JPanel {

    private JScrollPane scroll;
    private GeneralManager manager;
    private JTable productsTable;
    private ArrayList<Product> products;


    public ControlTable(GeneralManager manager) {
        this.manager = manager;
        this.setBackground(Color.WHITE);
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(new BorderLayout(0, 0));
        initComponents();
        createTable();
    }

    private void initComponents() {
        JLabel tableLbl = new JLabel(Constants.TIP_TEXT);
        tableLbl.setFont(Constants.TABLE_FONT);
        this.add(tableLbl, BorderLayout.NORTH);

        scroll = new JScrollPane();
        this.add(scroll);

        productsTable = new JTable();
        productsTable.setBackground(Color.WHITE);
        productsTable.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        productsTable.setOpaque(false);
        scroll.setViewportView(productsTable);

    }

    public void createTable() {
        products = getAllProducts();
        Object[][] data = getDataByTitle(Constants.HEADERS);
        generateTable(Constants.HEADERS, data);
    }

    private ArrayList<Product> getAllProducts() {
        return manager.getProducts();
    }

    private Object[][] getDataByTitle(String[] titleList) {
        String[][] fullData = new String[products.size()][titleList.length];

        for (int x = 0; x < fullData.length; x++) {
            fullData[x][0] = products.get(x).getCode() + "";
            fullData[x][1] = products.get(x).getName() + "";
            fullData[x][2] = products.get(x).getQuantity() + "";
            fullData[x][3] = products.get(x).getDescription() + "";
            fullData[x][4] = products.get(x).getFirstPrice() + "";
            fullData[x][5] = products.get(x).getSecondPrice() + "";
            fullData[x][6] = products.get(x).getThirdPrice() + "";
        }
        return fullData;
    }

    private void generateTable(String[] titles, Object[][] data) {
        TableProductModel model = new TableProductModel(data, titles);
        productsTable.setModel(model);
        productsTable.getColumnModel().getColumn(0).setCellRenderer(new ProductsRows(Constants.TYPE_NUMERIC));
        productsTable.getColumnModel().getColumn(2).setCellRenderer(new ProductsRows(Constants.TYPE_NUMERIC));
        productsTable.getColumnModel().getColumn(4).setCellRenderer(new ProductsRows(Constants.TYPE_NUMERIC));
        productsTable.getColumnModel().getColumn(5).setCellRenderer(new ProductsRows(Constants.TYPE_NUMERIC));
        productsTable.getColumnModel().getColumn(6).setCellRenderer(new ProductsRows(Constants.TYPE_NUMERIC));
        for (int i = 1; i < titles.length; i++) {
            if (i != 2) {
                productsTable.getColumnModel().getColumn(i).setCellRenderer(new ProductsRows(Constants.TYPE_TEXT));
            }
        }
        productsTable.getTableHeader().setReorderingAllowed(false);
        productsTable.setRowHeight(25);
        productsTable.setGridColor(new java.awt.Color(0, 0, 0));
        productsTable.getColumnModel().getColumn(0).setPreferredWidth(160);
        productsTable.getColumnModel().getColumn(1).setPreferredWidth(160);
        productsTable.getColumnModel().getColumn(2).setPreferredWidth(160);
        productsTable.getColumnModel().getColumn(3).setPreferredWidth(380);
        productsTable.getColumnModel().getColumn(4).setPreferredWidth(200);
        productsTable.getColumnModel().getColumn(5).setPreferredWidth(200);
        productsTable.getColumnModel().getColumn(6).setPreferredWidth(200);
        productsTable.getColumnModel().getColumn(7).setPreferredWidth(200);
        JTableHeader jtableHeader = productsTable.getTableHeader();
        jtableHeader.setDefaultRenderer(new ProductsHeaders());
        productsTable.setTableHeader(jtableHeader);
        scroll.setViewportView(productsTable);
    }

}
