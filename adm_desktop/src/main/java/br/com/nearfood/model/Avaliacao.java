
package br.com.nearfood.model;

import java.time.LocalDateTime;

// registrar avaliações dos usuarios sobre os restaurantes

public class Avaliacao {
    
    private Integer id;
    
    private Usuario usuario;
    
    private Restaurant restaurant;
    
    private Integer rating; // 1 a 5 (estrelas)
    
    private String comentario;
    
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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getCriado_em() {
        return criado_em;
    }

    public void setCriado_em(LocalDateTime criado_em) {
        this.criado_em = criado_em;
    }
    
    
}
