package model.dao;

import java.util.List;

import model.entities.Produto;

public interface ProdutoDao {
	
	void cadastrar(Produto obj);
	List<Produto> buscar(String nome);
	void atualizar(Produto obj);
	void remover(Produto prod);
	List<Produto> listarProdutos();
}
