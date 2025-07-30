package br.com.nearfood.controller;

import br.com.nearfood.model.Produto;
import br.com.nearfood.services.ProdutoService;
import java.math.BigDecimal;

public class ProdutoController {
    private ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    public void cadastrarProduto(String nome, String descricao, String precoStr, String categoria, boolean disponivel) {
        try {
            BigDecimal preco = new BigDecimal(precoStr);
            Produto produto = new Produto(gerarId(), nome, descricao, preco, categoria, disponivel);
            service.cadastrarProduto(produto);
            System.out.println("Produto cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    private int gerarId() {
        // Lógica simples para gerar ID - pode ser melhorada
        return (int) (Math.random() * 10000);
    }

    public void listarProdutos() {
        service.listarProdutos().forEach(produto -> {
            System.out.println(produto.getNome() + " - " + produto.getPreco());
        });
    }
}
