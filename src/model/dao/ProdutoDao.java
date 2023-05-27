package model.dao;

import java.util.List;

import model.entities.Produto;

public interface ProdutoDao {
	
	void cadastrar(Produto obj);
	//o de exibir seria o toString???
	Produto buscar(String nome);
	void atualizar(Produto obj);
	void remover(String nome);
	List<Produto> listarProdutos();
	

}
