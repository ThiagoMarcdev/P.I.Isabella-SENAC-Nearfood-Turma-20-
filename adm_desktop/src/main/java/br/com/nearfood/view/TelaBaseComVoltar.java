package br.com.nearfood.view;

import br.com.nearfood.view.TelaLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public abstract class TelaBaseComVoltar extends JFrame {
    //classe servira para ser repetida para usar metodos repetidos, em botões voltar

    protected JButton btnVoltar;

    public void TelaBaseComVoltar() {
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltarParaLogin();
            }
        });
        add(btnVoltar); // Aqui é simples, mas no seu layout real, colocaria no lugar certo

    }

    //metodo que sera usado para herdar para outras classes
    public void voltarParaLogin() {
        dispose();
        new TelaLogin().setVisible(true);

    }

}
