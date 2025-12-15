package br.com.nearfood.models;

public class Usuario {
    //id mas o id ja é gerado automatico
    private String username;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String telefone;
    private String tipo_usuario;

    public Usuario(String username, String first_name, String last_name, String password, String email, String tipo_usuario, String telefone) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.tipo_usuario = tipo_usuario;
        this.telefone = telefone;
    }
    
    public String getUsername() { return username;}
    public void setUsername(String username) { this.username = username;}
    
    public String getFirst_name() { return first_name; }
    public void setFirst_name(String first_name) { this.first_name = first_name; }
    
    public String getLast_name() { return last_name;}
    public void setLastName(String last_name) { this.last_name = last_name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTipo_usuario() { return tipo_usuario; }
    public void setTipoUsuario(String tipo) { this.tipo_usuario = tipo; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

}
