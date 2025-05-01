package br.com.nearfood.view;

import javax.swing.*;

public class TelaCadastro extends JFrame {

    public TelaCadastro() {
        
        initComponents();
        setTitle("Cadastro");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        
    }
    
    private void initComponents(){
        JLabel lblNome = new JLabel("Insira seu nome completo");
        lblNome.setBounds(50, 50, 80, 25);
        add(lblNome);
        
        
    }
}
