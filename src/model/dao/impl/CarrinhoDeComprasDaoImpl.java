package model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import exception.StoreNotFoundException;
import model.dao.CarrinhoDeComprasDao;
import model.dao.CompradorDao;
import model.dao.DaoFactory;
import model.dao.HistoricoDeComprasDao;
import model.dao.LojaDao;
import model.dao.ProdutoDao;
import model.entities.Compra;
import model.entities.Comprador;
import model.entities.Loja;
import model.entities.Pedido;
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
	public void comprar(Comprador comprador, Produto produtoComprado, int quantidade) throws StoreNotFoundException {
		HistoricoDeComprasDao historicoDao = DaoFactory.criarHistoricoDeComprasDao();
		ProdutoDao produtoDao = DaoFactory.criarProdutoDao();
		LojaDao lojaDao = DaoFactory.criarLojaDao();
		Loja loja = lojaDao.buscarPeloEmail(produtoComprado.getEmailLoja());

		int novaQuant = produtoComprado.getQuantidade() - quantidade;

		List<Produto> produtos = loja.getProdutos();

		Produto produtoRemovido = null;
		for (Produto produto : loja.getProdutos()) {
			if (produto.getNome().equals(produtoComprado.getNome())) {
				produtoRemovido = produto;
				break;
			}
		}

		// se a quant restante for zero faz isso
		if (novaQuant == 0) {
			produtoDao.remover(produtoComprado);
			remover(comprador, produtoComprado);
			loja.getProdutos().remove(produtoRemovido);
			lojaDao.atualizar(loja);

			// se sobrar algo diferente de zero faz isso
		} else {
			// atualizo a quantidade do produto no JSON chamado produtosExistentes
			produtoComprado.setQuantidade(novaQuant);

			// Atualizar o produto no arquivo JSON
			produtoDao.atualizar(produtoComprado);

			// Adicionar a compra no histórico
			historicoDao.adicionar(comprador, new Compra(new Pedido(produtoComprado, quantidade)));

			for (Produto produto : produtos) {
				// Realize as verificações para encontrar o produto específico
				if (produto.getNome().equals(produtoComprado.getNome())) {
					produto.setQuantidade(novaQuant);
					break;
				}
			}

				// atualiza a quant do produto disponivel na loja
				loja.setProdutos(produtos);

				// Atualizar a loja no arquivo JSON
				lojaDao.atualizar(loja);
			

		}
		
	}
}