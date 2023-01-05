package com.sc.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ProductsRows extends DefaultTableCellRenderer {

    private String type;
    private JLabel label;

    public ProductsRows() {
        type = "text";
        label = new JLabel();
    }

    public ProductsRows(String type) {
        this.type = type;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        Color background = null;
        Color backgroundDefault = new Color(192, 192, 192);
        Color backgroundSelected = new Color(140, 140, 140);
        if (selected) {
            this.setBackground(backgroundDefault);
        } else {
            this.setBackground(Color.white);
        }

        if (type.equalsIgnoreCase(Constants.TYPE_TEXT)) {
            if (focused) {
                background = backgroundSelected;
            } else {
                background = backgroundDefault;
            }
            this.setHorizontalAlignment(JLabel.LEFT);
            this.setText((String) value);
            this.setBackground((selected) ? background : Color.WHITE);
            this.setFont(Constants.TEXT_FONT);
            return this;
        }
        if (type.equalsIgnoreCase(Constants.TYPE_NUMERIC)) {
            if (focused) {
                background = backgroundSelected;
            } else {
                background = backgroundDefault;
            }
            this.setHorizontalAlignment(JLabel.CENTER);
            this.setText((String) value);
            this.setForeground((selected) ? new Color(255, 255, 255) : new Color(32, 117, 32));
            this.setBackground((selected) ? background : Color.WHITE);
            this.setFont(Constants.MAIN_FONT);
            return this;
        }
        return this;
    }
}
