package br.com.nearfood.models;

import com.google.gson.annotations.SerializedName; 
// Se não tiver essa importação, adicione a lib Gson ao projeto ou remova a anotação e renomeie a variavel

public class Usuario {

    // 1. O ID é OBRIGATÓRIO aqui para o Java saber quem editar, 
    // mesmo que o banco gere sozinho.
    private Long id; 

    private String username;
    private String first_name;
    private String last_name;
    private String email;
    
    // O campo password geralmente não retornamos do banco por segurança no GET,
    // mas deixamos aqui caso precise enviar no PUT.
    private String password; 
    
    private String telefone;

    // 2. No Django seu campo chama 'tipo', aqui chama 'tipo_usuario'.
    // Usamos essa anotação para mapear sem precisar mudar o nome da variável.
    // Ou você renomeia a variável abaixo apenas para "tipo".
    @SerializedName("tipo") 
    private String tipo_usuario;

    // ==========================================
    // 3. CONSTRUTOR VAZIO (Essencial para o Gson)
    // ==========================================
    public Usuario() {
    }

    // Construtor completo (Opcional, mas útil)
    public Usuario(Long id, String username, String first_name, String last_name, String email, String telefone) {
        this.id = id;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.telefone = telefone;
    }
    
    // ==========================================
    // GETTERS E SETTERS
    // ==========================================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username;}
    public void setUsername(String username) { this.username = username;}
    
    public String getFirst_name() { return first_name; }
    public void setFirst_name(String first_name) { this.first_name = first_name; }
    
    public String getLast_name() { return last_name;}
    public void setLast_name(String last_name) { this.last_name = last_name; } // Corrigi o nome do método para padrao camelCase

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTipo_usuario() { return tipo_usuario; }
    public void setTipo_usuario(String tipo_usuario) { this.tipo_usuario = tipo_usuario; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}