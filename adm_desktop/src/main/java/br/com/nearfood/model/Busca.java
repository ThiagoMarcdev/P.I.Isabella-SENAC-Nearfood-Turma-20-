
package br.com.nearfood.model;

import java.time.LocalDateTime;


public class Busca {
    
    private Integer id;
    
    private Usuario usuario;
    
    private String Termo_pesquisa;
    
    private String localizacao; //puxa a localização do usuario
    
    private LocalDateTime criado_em;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTermo_pesquisa() {
        return Termo_pesquisa;
    }

    public void setTermo_pesquisa(String Termo_pesquisa) {
        this.Termo_pesquisa = Termo_pesquisa;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public LocalDateTime getCriado_em() {
        return criado_em;
    }

    public void setCriado_em(LocalDateTime criado_em) {
        this.criado_em = criado_em;
    }

    
    
}
