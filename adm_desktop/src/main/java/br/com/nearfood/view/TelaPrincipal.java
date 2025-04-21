package br.com.nearfood.view;

import javax.swing.*;
import java.awt.*;

//package br.com.nearfood.view;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Principal - NearFoodADM");
        setSize(1280, 720);
        setLocationRelativeTo(null); // Centraliza a janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // -------- Barra Superior --------
        JPanel barraSuperior = new JPanel();
        barraSuperior.setBackground(new Color(30, 144, 255));
        barraSuperior.setPreferredSize(new Dimension(0, 60));
        barraSuperior.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblUsuario = new JLabel("Olá, Admin");
        lblUsuario.setForeground(Color.WHITE);
        barraSuperior.add(lblUsuario);

        // -------- Menu Lateral --------
        JPanel menuLateral = new JPanel();
        menuLateral.setBackground(new Color(60, 63, 65));
        menuLateral.setPreferredSize(new Dimension(200, 0));
        menuLateral.setLayout(new BoxLayout(menuLateral, BoxLayout.Y_AXIS));

        String[] opcoes = { "Início", "Produtos", "Pedidos", "Relatórios", "Sair" };
        for (String opcao : opcoes) {
            JButton btn = new JButton(opcao);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuLateral.add(btn);
        }

        // -------- Conteúdo Central --------
        JPanel painelConteudo = new JPanel();
        painelConteudo.setBackground(Color.WHITE);
        painelConteudo.setLayout(new GridLayout(2, 2, 20, 20));
        painelConteudo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        /*for (int i = 1; i <= 7; i++) {
            JPanel card = new JPanel();
            card.setBackground(new Color(240, 240, 240));
            card.setBorder(BorderFactory.createTitledBorder("Card " + i));
            painelConteudo.add(card);
        }*/

        // -------- Adiciona tudo --------
        add(barraSuperior, BorderLayout.NORTH);
        add(menuLateral, BorderLayout.WEST);
        add(painelConteudo, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}