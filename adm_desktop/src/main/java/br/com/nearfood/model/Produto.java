
package br.com.nearfood.model;

import java.math.BigDecimal;

public class Produto {
    
    private long id;
    
    private String nome;
    
    private BigDecimal preco;
    
    private String categoria;
    
    private boolean status;
    
    private String imagemUrl;
    
    private Integer tempoPreparo;
    
    private boolean destaque;
    
    private Integer estoque;

    public Produto(int id, String nome, String categoria, BigDecimal preco, String imagemUrl, boolean status) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        this.status = status;
        this.imagemUrl = imagemUrl;
        this.tempoPreparo = tempoPreparo;
        this.destaque = destaque;
        this.estoque = estoque;
    }
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public Integer getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(Integer tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public boolean isDestaque() {
        return destaque;
    }

    public void setDestaque(boolean destaque) {
        this.destaque = destaque;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }
    
    
    
}
