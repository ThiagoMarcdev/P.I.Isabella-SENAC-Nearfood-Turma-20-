package br.com.nearfood.model;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    //#region Atributos de usuario
    private Integer id;
    
    private String nome;
    
    private String username;
    
    private String email;
    
    private String senha;
    
    private String localizacao;
    
    private List<Prato> pratosfavoritos;
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getPratosfavoritos() {
        return pratosfavoritos;
    }

    public void setPratosfavoritos(String pratosfavoritos) {
        this.pratosfavoritos = pratosfavoritos;
    }
    //#endregion
    
    
    

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
