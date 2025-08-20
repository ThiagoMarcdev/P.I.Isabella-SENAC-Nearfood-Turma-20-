package br.com.nearfood.controller;

import br.com.nearfood.view.TelaResetSenha;

/**
 * Controlador para reset de senha
 * Extende BaseController para reutilizar comportamento de voltar à tela de login
 */
public class ResetSenhaController extends BaseController {

    private TelaResetSenha telaResetSenha;

    public ResetSenhaController(TelaResetSenha telaResetSenha) {
        this.telaResetSenha = telaResetSenha;
    }

    public void voltarTelaLogin() {
        super.voltarTelaLogin(telaResetSenha);
    }
}
