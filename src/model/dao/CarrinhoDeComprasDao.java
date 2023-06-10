package model.dao;

import java.util.List;

import model.entities.Comprador;
import model.entities.Produto;

public interface CarrinhoDeComprasDao {
	
	void adicionar(Comprador comprador, Produto obj);
	void remover(Produto obj);
	List<Produto> listarProdutos(Comprador comprador);
	void comprar(Produto obj);

}
