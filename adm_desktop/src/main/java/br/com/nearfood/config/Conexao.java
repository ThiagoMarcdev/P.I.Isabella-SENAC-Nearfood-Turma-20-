package br.com.nearfood.config;

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
        final String SENHA = "NearFood1234#"; //mudando a senha para senha mais forte paa testes
                                            //senha antiga para usar na apresentaçao: pw_user_app

        return DriverManager.getConnection(SERVIDOR, USUARIO, SENHA);
    }

}
