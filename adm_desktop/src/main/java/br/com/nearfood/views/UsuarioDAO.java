
package br.com.nearfood.views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UsuarioDAO {
    // classe que cadastra informações no BD NearFoodDB
    
    public void cadastrarUsuario(String first_name, String last_name,String username,String email, String password, String tipo){
        String sql = "INSERT INTO tbl_Usuarios (username, first_name, last_name, email, password, tipo, is_superuser, is_staff, is_active) VALUES (?, ?, ?, ?, ?, ?, 0, 0, 1)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password); 
            stmt.setString(4, tipo);
            stmt.setString(5, first_name);
            stmt.setString(6, last_name);

            stmt.executeUpdate();
            System.out.println("✅ Usuário cadastrado!");
        } catch (SQLException e) {
            System.out.println("❌ Erro ao cadastrar: " + e.getMessage());
        }
    }
}
