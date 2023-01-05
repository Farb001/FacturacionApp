package com.sc.view;

import javax.swing.*;
import java.util.ArrayList;

public class SimpleView {

    public int readInt(String message){
        JLabel text = new JLabel(message);
        text.setFont(Constants.MAIN_FONT);
        return Integer.parseInt(JOptionPane.showInputDialog(null, text, "Leer " + message, JOptionPane.CLOSED_OPTION));
    }

    public ArrayList<Integer> readPrices(int total){
        ArrayList<Integer> prices = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            prices.add(readInt(Constants.READ_PRICE));
        }
        return prices;
    }

    public void showMessage(String message){
        JLabel messageInput = new JLabel(message);
        messageInput.setFont(Constants.MAIN_ITALIC_FONT);
        JOptionPane.showMessageDialog(null, messageInput, "Notificación", JOptionPane.CLOSED_OPTION);
    }
}
