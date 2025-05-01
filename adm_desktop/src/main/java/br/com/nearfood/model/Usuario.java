package br.com.nearfood.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {

    //MÉTODO PARA CADASTRO DE USUÁRIO
    public void cadastrar(String nome, String email, String senha) throws SQLException {
        String sql = "INSERT INTO tblUsuarios (nome, email, senha) VALUES (?, ?, ?)";
        try (Connection con = Conexao.getConexao(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.executeUpdate();

        }
    }

    //MÉTODO PARA LOGIN DE USUÁRIO
    public boolean login(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM tblUsuarios WHERE email = ? AND senha = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
}
