package br.com.nearfood.main;

import br.com.nearfood.views.TelaLoginNew;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLoginNew().setVisible(true));
    }
}

