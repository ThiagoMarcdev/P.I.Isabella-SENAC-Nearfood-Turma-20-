
package br.com.nearfood.view;

import javax.swing.*;

/**
 *
 * @author firmodev
 */
public class TelaResetSenha extends JFrame{
    
    public TelaResetSenha(){
        initComponents();
        setTitle("Redefinir Senha");
        setSize(400, 300);
        setLocationRelativeTo(null); //centraliza a janela na tela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//encerra o programa se fechada
        setResizable(false);
        setLayout(null);
        initComponents();
        
    }

    private void initComponents() {
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(50, 50, 80, 25);
        add(lblEmail);
        
        JTextField txtCampoEmail = new JTextField();
        txtCampoEmail.setBounds(130, 50, 200, 25);
        add(txtCampoEmail);
        
        JLabel lblNovaSenha = new JLabel("Nova Senha:");
        lblNovaSenha.setBounds(50, 90, 80, 25);
        add(lblNovaSenha);
        
        JPasswordField txtCampoSenha = new JPasswordField();
        txtCampoSenha.setBounds(130, 90, 200, 25);
        add (txtCampoSenha);
        
        JLabel lblConfirma = new JLabel("Confirmar:");
        lblConfirma.setBounds(50, 130, 80, 25);
        add(lblConfirma);
        
        JPasswordField txtCampoConfirmaSenha = new JPasswordField();
        txtCampoConfirmaSenha.setBounds(130, 130, 150, 30);
        add(txtCampoConfirmaSenha);
        
        JButton btnRedefinirSenha = new JButton("Redefinir");
        btnRedefinirSenha.setBounds(250, 200, 120, 30);
        add(btnRedefinirSenha);
        
    }
    
        public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        new TelaResetSenha().setVisible(true);
    });
}

    
}
