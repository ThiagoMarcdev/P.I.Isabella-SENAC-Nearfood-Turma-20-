
package br.com.nearfood.views;


public class Main {
    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();
        dao.cadastrarUsuario("teste" , "ferreira" ,"teste_user", "teste_user@gmail.com", "123456", "cliente");
    }
    
}
