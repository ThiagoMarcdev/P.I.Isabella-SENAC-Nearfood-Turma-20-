package br.com.nearfood.controller;

import br.com.nearfood.view.TelaCadastro;

/**
 * Controlador para cadastro de usuário
 * Extende BaseController para reutilizar comportamento de voltar à tela de login
 * @author firmodev
 */
public class CadastrarUsuarioController extends BaseController {

    private TelaCadastro telaCadastro;

    public CadastrarUsuarioController(TelaCadastro telaCadastro) {
        this.telaCadastro = telaCadastro;
    }

    public void voltarTelaLogin() {
        super.voltarTelaLogin(telaCadastro);
    }
}
