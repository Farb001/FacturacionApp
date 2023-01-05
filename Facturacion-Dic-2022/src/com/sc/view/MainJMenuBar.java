package com.sc.view;

import com.sc.controller.Commands;

import javax.swing.*;
import java.awt.event.ActionListener;

import static com.sc.view.Constants.JMENU_MAIN_FONT;

public class MainJMenuBar extends JMenuBar {

    private JMenu inventory, billing;
    private JMenuItem deleteInventory, updateInventory, addInventory, deleteBilling, updateBilling, addBilling;
    private ActionListener actionListener;

    public MainJMenuBar(ActionListener listener){
        this.actionListener = listener;
        inventory = addJMenu(inventory, Constants.INVENTORY_JMENU);
        addJMenuItem(inventory, addInventory, Constants.INVENTORY_JMENU_ADD, actionListener, Commands.ADD_PRODUCT.name());
        addJMenuItem(inventory, updateInventory, Constants.INVENTORY_JMENU_EDIT, actionListener, Commands.EDIT_PRODUCT.name());
        addJMenuItem(inventory, deleteInventory, Constants.INVENTORY_JMENU_DELETE, actionListener, Commands.DELETE_PRODUCT.name());
        add(inventory);

        billing = addJMenu(billing, Constants.BILLING_JMENU);
        addJMenuItem(billing, addBilling, Constants.BILLING_JMENU_ADD, actionListener, Commands.ADD_BILLING.name());
        addJMenuItem(billing, updateBilling, Constants.BILLING_JMENU_EDIT, actionListener, Commands.EDIT_BILLING.name());
        addJMenuItem(billing, deleteBilling, Constants.BILLING_JMENU_DELETE, actionListener, Commands.DELETE_BILLING.name());
        add(billing);
    }

    public JMenu addJMenu(JMenu jMenu, String tittle){
        jMenu = new JMenu(tittle);
        jMenu.setFont(JMENU_MAIN_FONT);
        return jMenu;
    }

    public void addJMenuItem( JMenu jMenu, JMenuItem jMenuItem, String tittle, ActionListener actionListener, String actionCommand){
        jMenuItem = new JMenuItem(tittle);
        jMenuItem.setFont(JMENU_MAIN_FONT);
        jMenuItem.addActionListener(actionListener);
        jMenuItem.setActionCommand(actionCommand);
        jMenu.add(jMenuItem);
    }
}
