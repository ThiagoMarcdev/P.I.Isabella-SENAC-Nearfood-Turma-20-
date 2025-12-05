package br.com.nearfood.models;

public class Usuario {
    //id mas o id ja é gerado automatico
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String telefone;
    private String tipo_usuario;

    public Usuario(String firstName, String lastName, String password, String email, String tipo_usuario, String telefone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.tipo_usuario = tipo_usuario;
        this.telefone = telefone;
    }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName;}
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTipo_usuario() { return tipo_usuario; }
    public void tipo_usuario(String tipo) { this.tipo_usuario = tipo; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

}
