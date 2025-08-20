package br.com.nearfood.controller;

import br.com.nearfood.view.TelaLogin;
import br.com.nearfood.view.TelaResetSenha;
import br.com.nearfood.view.TelaCadastro;

public class LoginController {

    private TelaLogin telalogin;

    public LoginController(TelaLogin telalogin) {
        this.telalogin = telalogin;

    }

    public void esqueceuSenha() {//metodo reset senha
        telalogin.dispose(); //fechando tela

        TelaResetSenha telaResetSenha = new TelaResetSenha(); //criando objeto
        telaResetSenha.setVisible(true); //mostrando tela

    }

    public void cadastrarUsuario() {        
        telalogin.dispose();
        TelaCadastro telaCadastro = new TelaCadastro(null); // substituído
        CadastrarUsuarioController controller = new CadastrarUsuarioController(telaCadastro);
        telaCadastro = new TelaCadastro(controller);
        telaCadastro.setVisible(true);
    }

}
