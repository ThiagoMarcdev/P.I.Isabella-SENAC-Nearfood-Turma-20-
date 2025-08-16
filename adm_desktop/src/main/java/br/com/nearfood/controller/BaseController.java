package br.com.nearfood.controller;

import br.com.nearfood.view.TelaLogin;
import javax.swing.JFrame;

/**
 * Classe base para controladores com função de voltar à tela de login
 * @author firmodev
 */
public abstract class BaseController {

    public void voltarTelaLogin(JFrame telaAtual) {
        // Fecha a tela atual
        telaAtual.dispose();

        // Abre a tela de login
        TelaLogin telaLogin = new TelaLogin();
        telaLogin.setVisible(true);
    }
}
