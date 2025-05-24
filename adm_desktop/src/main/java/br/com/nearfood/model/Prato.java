
package br.com.nearfood.model;

//import java.awt.List;
import java.util.List;


public class Prato {
    
    private Integer id;
    
    private String nome;
    
    private String descricao;
    
    private List<String> tags; //vegano, doce, etc..
    
    private double preco;
    
    private Restaurant restaurant; //aqui sera uma foreing key

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List getTags() {
        return tags;
    }

    public void setTags(List tags) {
        this.tags = tags;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    
    
}
