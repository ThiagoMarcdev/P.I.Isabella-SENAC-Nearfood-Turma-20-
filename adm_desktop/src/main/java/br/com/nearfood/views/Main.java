package br.com.nearfood.views;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new telaLogin().setVisible(true));
    }
}

