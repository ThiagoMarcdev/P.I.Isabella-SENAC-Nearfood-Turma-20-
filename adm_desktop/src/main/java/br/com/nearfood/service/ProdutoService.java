

package br.com.nearfood.services;

import br.com.nearfood.model.Produto;
import br.com.nearfood.repository.ProdutoRepository;
import java.util.List;

public class ProdutoService {
    
    private ProdutoRepository repository;
    
    public ProdutoService(ProdutoRepository repository){
        this.repository = repository;
        
    }
    
    
    public void cadastrarProduto(Produto produto){
        if (produto.getPreco().doubleValue() <= 0){
            throw new IllegalArgumentException("Preço deve ser maior do que 0");
        }
        repository.salvar(produto);
    }
    
    public List<Produto> listarProdutos() {
        return repository.buscaTodos();
    }
    
    public Produto buscaProdutoPorId(int id){
        return repository.encontrarPorId(id);
        
    }
    
    public void atualizaProduto(Produto produto){
        repository.atualizar(produto);
        
    }
    
    public void removerProduto(int id){
        repository.delete(id);
        
    }
    
}
