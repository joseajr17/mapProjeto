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
					p -> p.getNome().equalsIgnoreCase(obj.getNome()) && p.getEmailLoja().equals(obj.getEmailLoja()));

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
	public void remover(Comprador comprador, Produto obj) {
		List<Produto> carrinhoDeCompras = comprador.getCarrinhoDeCompras();

		if (carrinhoDeCompras == null || carrinhoDeCompras.isEmpty()) {
			System.out.println("O carrinho de compras está vazio.");
			return;
		}

		// Verificar se o produto está no carrinho
		boolean produtoEncontrado = carrinhoDeCompras.stream().anyMatch(
				p -> p.getNome().equalsIgnoreCase(obj.getNome()) && p.getEmailLoja().equals(obj.getEmailLoja()));

		if (!produtoEncontrado) {
			System.out.println("O produto da mesma loja com o mesmo nome não está presente no carrinho.");
			return;
		}

		carrinhoDeCompras.removeIf(
				p -> p.getNome().equalsIgnoreCase(obj.getNome()) && p.getEmailLoja().equals(obj.getEmailLoja()));

		comprador.setCarrinhoDeCompras(carrinhoDeCompras);

		compradorDao.atualizar(comprador);
	}

	@Override
	public List<Produto> listarProdutos(Comprador comprador) {
		List<Produto> produtos = new ArrayList<>();

		List<Produto> carrinhoDeCompras = comprador.getCarrinhoDeCompras();

		if (carrinhoDeCompras != null) {
			produtos.addAll(carrinhoDeCompras);
		}

		return produtos;
	}

	@Override
	public void comprar(Comprador comprador, Produto obj) {
		
	}
}
