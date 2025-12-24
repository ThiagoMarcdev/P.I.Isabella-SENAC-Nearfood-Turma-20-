package br.com.nearfood.models;

import java.io.File;

public class Restaurante {

    private String nome;
    private String descricao;
    private File imagem;

    public Restaurante(String nome, String descricao, File imagem) {
        this.nome = nome;
        this.descricao = descricao;
        this.imagem = imagem;
    }

    // ===== GETTERS =====

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public File getImagem() {
        return imagem;
    }

    // ===== SETTERS =====

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setImagem(File imagem) {
        this.imagem = imagem;
    }
}
