package br.com.nearfood.views;

import java.sql.*;

public class Usuario {
    
    public void cadastrar(String nome, String telefone, String cnpj, String senha, String confirmarSenha) throws SQLException {
        String sql = "INSERT INTO tblUsuarios (nome, telefone, cnpj, senha, confirmarSenha) VALUES (?, ?, ?)";
        try (Connection con = Conexao.getConexao(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, telefone);
            stmt.setString(3, cnpj);
            stmt.setString(4, senha);
            stmt.setString(5, confirmarSenha);
            stmt.executeUpdate();
        }
    }
    
     public boolean login(String cnpj, String senha) throws SQLException {
        String sql = "SELECT * FROM tblUsuarios WHERE cnpj = ? AND senha = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
     
    public void limparUsuarios() throws SQLException {
        try (Connection con = Conexao.getConexao(); Statement stmt = con.createStatement()) {
            stmt.execute("DELETE FROM tblUsuarios");
        }
    }
    
    public String getNome(String cnpj)throws SQLException {
        String sql = "SELECT nome FROM tblUsuarios WHERE cnpj = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nome");
            } else {
                return null; // ou lançar uma exceção, dependendo do que você quer fazer se não encontrar o email
            }
        }
    }
    
}

