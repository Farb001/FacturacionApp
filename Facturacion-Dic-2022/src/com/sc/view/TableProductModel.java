package com.sc.view;

import javax.swing.table.DefaultTableModel;

public class TableProductModel extends DefaultTableModel {

    private String[] headers;
    private Object[][] data;

    public TableProductModel(Object[][] data, String[] headers) {
        super();
        this.headers = headers;
        this.data = data;
        setDataVector(data, headers);
    }

    public TableProductModel() {
    }

    public boolean isCellEditable(int row, int column) {
        return false;

    }

}
