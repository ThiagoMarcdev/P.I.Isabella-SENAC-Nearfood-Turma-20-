package br.com.senac.backup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Backup {

    public void fazerBackup() throws SQLException {
        String sql = "EXEC sp_FazerBackup";
        
        try (
            // Criando conexão com o banco
            Connection con = Conexao.getConexao();
            PreparedStatement stmt = con.prepareStatement(sql)
        ) 
        {
            //EXECUTA O COMANDO EXPECIFICADO NA STRING "SQL"
            stmt.executeUpdate();
        }
    }
}
