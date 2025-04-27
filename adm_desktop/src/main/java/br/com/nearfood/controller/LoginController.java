
package br.com.nearfood.controller;

import br.com.nearfood.view.TelaLogin;
import br.com.nearfood.view.TelaResetSenha;
import java.util.HashSet;

        
public class LoginController {
    
    private TelaLogin telalogin;
    
    public LoginController(TelaLogin telalogin){
        this.telalogin = telalogin;
        
    }
        
    public void esqueceuSenha(){//metodo reset senha
        telalogin.dispose(); //fechando tela
        
        TelaResetSenha telaResetSenha = new TelaResetSenha(); //criando objeto
        telaResetSenha.setVisible(true); //mostrando tela
        
    }
}
