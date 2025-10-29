package br.com.nearfood.views;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
  public static Connection getConexao() throws SQLException {
        final String SERVIDOR = "jdbc:sqlserver://127.0.0.1:1433;databaseName=TesteLogin;encrypt=false;trustServerCertificate=true";
        final String USUARIO = "sa";
        final String SENHA = "pw_user_app";
        
        return DriverManager.getConnection(SERVIDOR, USUARIO, SENHA);
        
    }   
}

