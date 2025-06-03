package br.com.nearfood.repository;

import br.com.nearfood.model.Produto;
import java.util.List;
import java.util.ArrayList;

public class ProdutoRepository {

    private List<Produto> produtos = new ArrayList<>();

    public void salvar(Produto produto) {
        produtos.add(produto);

    }

    public List<Produto> buscaTodos() {
        return new ArrayList<>(produtos);
    }

    public Produto encontrarPorId(int id) {
        return produtos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

    }
    
    public void atualizar (Produto produto){
        delete(produto.getId());
        salvar(produto);
        
        
    }
    
    public void delete(int id){
        produtos.removeIf(p -> p.getId() == id);
        
    }
}
