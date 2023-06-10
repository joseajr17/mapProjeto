package model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import model.dao.CarrinhoDeComprasDao;
import model.dao.CompradorDao;
import model.dao.DaoFactory;
import model.entities.Comprador;
import model.entities.Produto;

public class CarrinhoDeComprasDaoImpl implements CarrinhoDeComprasDao {

	private static CompradorDao compradorDao = DaoFactory.criarCompradorDao();

	@Override
	public void adicionar(Comprador comprador, Produto obj) {

		List<Produto> carrinhoDeCompras = comprador.getCarrinhoDeCompras();

		if (carrinhoDeCompras == null) {
			carrinhoDeCompras = new ArrayList<>();
		} else {
			// Verificar se o produto já está no carrinho
			boolean produtoRepetido = carrinhoDeCompras.stream().anyMatch(
					p -> p.getNome().equalsIgnoreCase(obj.getNome()) 
						 && p.getEmailLoja().equals(obj.getEmailLoja()));

			if (produtoRepetido) {
				System.out.println("Esse produto dessa loja já está no seu carrinho.");
				return;
			}
		}
		carrinhoDeCompras.add(obj);

		comprador.setCarrinhoDeCompras(carrinhoDeCompras);

		compradorDao.atualizar(comprador);
		System.out.println("Produto adicionado ao carrinho com sucesso!");
	}

	@Override
	public void remover(Produto obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Produto> listarProdutos(Comprador comprador) {
		return null;
	}

	@Override
	public void comprar(Produto obj) {
		// TODO Auto-generated method stub

	}

}
