package com.sc.view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ProductsHeaders implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JComponent jcomponent = null;

        if (value instanceof String) {
            jcomponent = new JLabel((String) value);
            ((JLabel) jcomponent).setHorizontalAlignment(SwingConstants.CENTER);
            ((JLabel) jcomponent).setSize(30, jcomponent.getWidth());
            ((JLabel) jcomponent).setPreferredSize(new Dimension(6, jcomponent.getWidth()));
        }

        jcomponent.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(255, 255, 255)));
        jcomponent.setOpaque(true);
        jcomponent.setBackground(new Color(65, 65, 65));
        jcomponent.setToolTipText(Constants.TIP_TEXT);
        jcomponent.setForeground(Color.white);

        return jcomponent;
    }
}
