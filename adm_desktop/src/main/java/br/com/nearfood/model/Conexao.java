package br.com.nearfood.model;

/*Essa classe é para fazer toda a configuração do nosso banco de dados
    utilizado. Objetivo é centralizar configurações referentes a isso
    nesta classe para facil manutenção e consequentemente facil escalabilidade.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection getConexao() throws SQLException {
        final String SERVIDOR = "jdbc:sqlserver://127.0.0.1:1433;databaseName=NearFoodDB;"
                + "encrypt=false;trustServerCertificate=true";
        final String USUARIO = "sa";
        final String SENHA = "Nearfood@2025";
        return DriverManager.getConnection(SERVIDOR, USUARIO, SENHA);
    }

}
