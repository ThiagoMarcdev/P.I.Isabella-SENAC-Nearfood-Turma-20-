package br.com.nearfood.dao;

import br.com.nearfood.config.Conexao;
import br.com.nearfood.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Método para cadastrar usuário
    public void salvar(Usuario u) throws SQLException {
        String sql = "INSERT INTO tblUsuarios (nome, email, senha) VALUES (?, ?, ?)";
        try (Connection con = Conexao.getConexao(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getSenha());
            stmt.executeUpdate();
        }
    }

    // Método para login de usuário
    public boolean login(Usuario u) throws SQLException {
        
     
        String sql = "SELECT * FROM tblUsuarios WHERE email = ? AND senha = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getSenha());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
    
    
   public boolean validarLogin(String email, String senha) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Usuarios WHERE email = ? AND senha = ?";
        
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            stmt.setString(2, senha);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; // Retorna true se encontrar 1 ou mais registros
                }
            }
        }
        return false;
   }

    // Opcional: buscar usuário completo pelo email
    public Usuario buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM tblUsuarios WHERE email = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                // Preencher outros campos se houver
                return u;
            }
        }
        return null;
    }
}
