package br.com.nearfood.service;

import br.com.nearfood.models.Restaurante;

public class RestauranteService {

    // Simula persistência em memória
    private static Restaurante restauranteSalvo;

    // ===== SALVAR (CREATE / UPDATE) =====
    public void salvar(Restaurante restaurante) {
        restauranteSalvo = restaurante;
        System.out.println("Restaurante salvo com sucesso!");
    }

    // ===== BUSCAR (READ) =====
    public Restaurante buscar() {
        return restauranteSalvo;
    }
}
