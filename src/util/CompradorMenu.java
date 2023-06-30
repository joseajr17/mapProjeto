package util;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import exception.StoreNotFoundException;
import model.dao.AvaliacaoDao;
import model.dao.CarrinhoDeComprasDao;
import model.dao.CompradorDao;
import model.dao.DaoFactory;
import model.dao.HistoricoDeComprasDao;
import model.dao.LojaDao;
import model.dao.ProdutoDao;
import model.entities.Avaliacao;
import model.entities.Compra;
import model.entities.Comprador;
import model.entities.Loja;
import model.entities.Pedido;
import model.entities.Produto;

public class CompradorMenu {
	private static CompradorDao compradorDao = DaoFactory.criarCompradorDao();
	private static LojaDao lojaDao = DaoFactory.criarLojaDao();
	private static ProdutoDao produtoDao = DaoFactory.criarProdutoDao();
	private static CarrinhoDeComprasDao carrinhoDeComprasDao = DaoFactory.criarCarrinhoDeComprasDao();
	private static HistoricoDeComprasDao historicoDeComprasDao = DaoFactory.criarHistoricoDeComprasDao();
	private static AvaliacaoDao avaliacaoDao = DaoFactory.criarAvaliacaoDao();

	private static Scanner sc = new Scanner(System.in);

	public void exibirMenuComprador(Comprador comprador) {
		int opcao;

		do {
			System.out.println("----- MENU DO COMPRADOR -----");
			System.out.println("1. Buscar loja");
			System.out.println("2. Listar todas as lojas");
			System.out.println("3. Buscar produto");
			System.out.println("4. Listar todos os produtos");
			System.out.println("5. Listar todos os produtos de uma loja específica");
			System.out.println("6. Listar produtos do carrinho de compras");
			System.out.println("7. Produtos disponíveis para compra");
			System.out.println("8. Ver histórico de compras");
			System.out.println("9. Ir para a edição de perfil");
			System.out.println("10. Excluir perfil");
			System.out.println("0. Sair");
			System.out.print("Escolha uma opção: ");

			try {
				opcao = sc.nextInt();
				sc.nextLine();

				switch (opcao) {
				case 1:
					buscarLoja();
					break;
				case 2:
					listarTodasAsLojas();
					break;
				case 3:
					buscarProduto();
					break;
				case 4:
					listarTodosOsProdutos();
					break;
				case 5:
					listarProdutosDeLojaEspecifica(comprador);
					break;
				case 6:
					listarProdutosDoCarrinho(comprador);
					break;
				case 7:
					listarProdutosParaCompra(comprador);
					break;
				case 8:
					verHistoricoDeCompras(comprador);
					break;
				case 9:
					editarPerfilComprador(comprador);
					break;
				case 10:
					excluirPerfilComprador(comprador);
					opcao = 0;
					break;
				case 11:
					testarListagemAvaliacoes(comprador);
				case 0:
					System.out.println("Saindo do menu do comprador...");
					break;
				default:
					System.out.println("Opção inválida. Tente novamente.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Entrada inválida. Digite um número inteiro.");
				sc.nextLine(); // Limpar o buffer do scanner
				opcao = -1; // Definir um valor inválido para continuar no loop
			}
		} while (opcao != 0);
	}

	private void testarListagemAvaliacoes(Comprador comprador) {
		// TODO Auto-generated method stub

	}

	private void buscarLoja() {

		System.out.print("Digite o nome da loja: ");
		String nomeLoja = sc.nextLine();

		Loja loja = lojaDao.buscarPeloNome(nomeLoja);

		System.out.println("Nome: " + loja.getNome());
		System.out.println("Endereço: " + loja.getEndereco());
		System.out.println("Email: " + loja.getEmail());
		System.out.println();
	}

	private void listarTodasAsLojas() {
		List<Loja> lojas = lojaDao.listarLojas();

		if (lojas.isEmpty()) {
			System.out.println("Não há lojas cadastradas.");
		} else {
			System.out.println("Lista de todas as lojas:");
			for (Loja loja : lojas) {
				System.out.println(loja.getNome());
			}
		}
	}

	private void buscarProduto() {

		//////// ta exibindo apenas o primeiro produto do arquivo JSON/////////

		System.out.print("Digite o nome do produto: ");
		String nomeProduto = sc.nextLine();

		List<Produto> produtos = produtoDao.buscar(nomeProduto);

		if (produtos != null)
			produtos.forEach(System.out::println);
		else {
			System.out.println("Não existe produto com esse nome!");
			return;
		}

	}

	private void listarTodosOsProdutos() {
		List<Produto> produtos = produtoDao.listarProdutos();

		if (produtos.isEmpty()) {
			System.out.println("Não há produtos cadastrados.");
		} else {
			System.out.println("Lista de todos os produtos:");
			for (Produto produto : produtos) {
				System.out.println(produto.getNome());
			}
			System.out.println();
		}
	}

	private void listarProdutosDeLojaEspecifica(Comprador comprador) {
		System.out.print("Digite o nome da loja: ");
		String nomeLoja = sc.nextLine();

		// Buscar a loja pelo nome
		Loja lojaEncontrada = lojaDao.buscarPeloNome(nomeLoja);

		// Exibir os resultados da busca
		if (lojaEncontrada == null) {
			System.out.println("Loja não encontrada.");
		} else {
			List<Produto> produtosDaLoja = lojaEncontrada.getProdutos();
			if (produtosDaLoja == null || produtosDaLoja.isEmpty()) {
				System.out.println("Nenhum produto encontrado para a loja informada.");
			} else {
				System.out.println("Produtos da loja " + nomeLoja + ":");
				for (int i = 0; i < produtosDaLoja.size(); i++) {
					Produto produto = produtosDaLoja.get(i);
					System.out.println((i + 1) + ". " + produto);
				}

				System.out.print("Digite o número do produto que deseja adicionar ao carrinho (ou 0 para voltar): ");
				int escolha = sc.nextInt();
				sc.nextLine();

				if (escolha == 0) {
					// Voltar para o menu
					return;
				} else if (escolha >= 1 && escolha <= produtosDaLoja.size()) {
					Produto produtoEscolhido = produtosDaLoja.get(escolha - 1);
					System.out.println("Produto escolhido: " + produtoEscolhido);

					adicionarProdutoAoCarrinho(comprador, produtoEscolhido);
				} else {
					System.out.println("Escolha inválida!");
				}
			}
		}
	}

	private void adicionarProdutoAoCarrinho(Comprador comprador, Produto produto) {
		System.out.print("Deseja adicionar o produto ao carrinho (S/N)? ");
		String resposta = sc.nextLine();

		if (resposta.equalsIgnoreCase("S")) {
			// Obtenha o objeto Comprador atualizado
			Comprador compradorAtualizado = compradorDao.buscar(comprador.getEmail());

			// Agora você pode adicionar o produto ao carrinho de compras
			carrinhoDeComprasDao.adicionar(compradorAtualizado, produto);
			System.out.println("Produto adicionado ao carrinho com sucesso.");
		} else {
			System.out.println("Produto não foi adicionado ao carrinho.");
		}
	}

	private void listarProdutosDoCarrinho(Comprador comprador) {
		List<Produto> produtos = carrinhoDeComprasDao.listarProdutos(comprador);
		if (produtos.isEmpty()) {
			System.out.println("Não há produtos no carrinho.");
		} else {
			System.out.println("Lista de todos os produtos do seu carrinho:");
			for (int i = 0; i < produtos.size(); i++) {
				Produto produto = produtos.get(i);
				System.out.println((i + 1) + ". " + produto);
			}
			System.out.println();

			System.out.print("Digite o número do produto que deseja remover (ou 0 para voltar): ");
			int escolha = sc.nextInt();
			sc.nextLine();

			if (escolha == 0) {
				// Voltar para o menu
				return;
			} else if (escolha >= 1 && escolha <= produtos.size()) {
				Produto produtoEscolhido = produtos.get(escolha - 1);
				System.out.println("Produto escolhido: " + produtoEscolhido.getNome());

				removerProdutoDoCarrinho(comprador, produtoEscolhido);
			} else {
				System.out.println("Escolha inválida!");
			}
		}
	}

	private void removerProdutoDoCarrinho(Comprador comprador, Produto produto) {
		System.out.print("Deseja remover o produto do carrinho (S/N)? ");
		String resposta = sc.nextLine();

		if (resposta.equalsIgnoreCase("S")) {
			// Obtenha o objeto Comprador atualizado
			Comprador compradorAtualizado = compradorDao.buscar(comprador.getEmail());

			// Remova o produto do carrinho de compras
			carrinhoDeComprasDao.remover(comprador, produto);
			System.out.println("Produto removido do carrinho com sucesso!");
		} else {
			System.out.println("Produto não foi removido do carrinho.");
		}
	}

	////

	private void listarProdutosParaCompra(Comprador comprador) {
		List<Produto> produtosDisponiveis = carrinhoDeComprasDao.listarProdutos(comprador);
		if (produtosDisponiveis.isEmpty()) {
			System.out.println("Não há produtos disponíveis para compra.");
		} else {
			System.out.println("Lista de todos os produtos disponíveis para compra:");
			for (int i = 0; i < produtosDisponiveis.size(); i++) {
				Produto produto = produtosDisponiveis.get(i);
				System.out.println((i + 1) + ". " + produto);
			}
			System.out.println();

			System.out.print("Digite o número do produto que deseja comprar (ou 0 para voltar): ");
			int escolha = sc.nextInt();
			sc.nextLine();

			if (escolha == 0) {
				// Voltar para o menu
				return;
			} else if (escolha >= 1 && escolha <= produtosDisponiveis.size()) {
				Produto produtoEscolhido = produtosDisponiveis.get(escolha - 1);
				System.out.println("Produto escolhido para compra: " + produtoEscolhido.getNome());

				// Chame o método para realizar a compra do produto escolhido
				realizarCompra(comprador, produtoEscolhido);
			} else {
				System.out.println("Escolha inválida!");
			}
		}

	}

	private void realizarCompra(Comprador comprador, Produto produtoEscolhido) {
		try {
			System.out.print("Deseja comprar o produto selecionado (S/N)? ");

			String resposta = sc.nextLine();

			if (resposta.equalsIgnoreCase("S")) {
				int quantidadeDesejada;
				do {
					System.out.print("Digite a quantidade do produto que deseja comprar: ");
					quantidadeDesejada = sc.nextInt();
					sc.nextLine();
					if (quantidadeDesejada <= 0 || quantidadeDesejada > produtoEscolhido.getQuantidade())
						System.out.println("Quantidade invalida.");
				} while (quantidadeDesejada <= 0 || quantidadeDesejada > produtoEscolhido.getQuantidade());

				// Obtenha o objeto Comprador atualizado
				Comprador compradorAtualizado = compradorDao.buscar(comprador.getEmail());
				carrinhoDeComprasDao.comprar(compradorAtualizado, produtoEscolhido, quantidadeDesejada);
				System.out.println("Produto comprado com sucesso!");
				carrinhoDeComprasDao.remover(compradorAtualizado, produtoEscolhido);

			} else {
				System.out.println("Produto não foi comprado.");
			}

		} catch (StoreNotFoundException e) {
			System.out.println("Loja não encontrada. Detalhes do erro: " + e.getMessage());
		}

	}

	////

	private void editarPerfilComprador(Comprador comprador) {
		System.out.print("Digite o seu CPF: ");
		String cpf = sc.nextLine();

		// Verificar se o CPF corresponde ao comprador logado
		if (!cpf.equals(comprador.getCpf())) {
			System.out.println("Você não digitou o seu CPF corretamente.");
			return;
		}

		System.out.println("CPF correto! Atualize as informações que desejar:");

		System.out.print("Novo nome (deixe em branco para manter o atual): ");
		String novoNome = sc.nextLine();
		comprador.setNome(novoNome);

		System.out.print("Novo email (deixe em branco para manter o atual): ");
		String novoEmail = sc.nextLine();
		comprador.setEmail(novoEmail);

		System.out.print("Nova senha (deixe em branco para manter a atual): ");
		String novaSenha = sc.nextLine();
		comprador.setSenha(novaSenha);

		System.out.print("Novo endereço (deixe em branco para manter o atual): ");
		String novoEndereco = sc.nextLine();
		comprador.setEndereco(novoEndereco);

		compradorDao.atualizar(comprador);
		System.out.println("Informações pessoais atualizadas com sucesso.");
	}

	private void excluirPerfilComprador(Comprador comprador) {
		System.out.println("----- EXCLUIR PERFIL DO COMPRADOR -----");

		System.out.print("Digite o seu CPF: ");
		String cpf = sc.nextLine();

		// Verificar se o CPF ou CNPJ corresponde ao do comprador logado
		if (!cpf.equals(comprador.getCpf())) {
			System.out.println("Você não digitou o CPF.");
			return;
		}

		System.out.println("Tem certeza disso(DIGITE S para confirmar)?");

		String resp = sc.nextLine();

		if (resp.equalsIgnoreCase("s")) {
			// Remover a loja do arquivo JSON
			compradorDao.remover(cpf);
			System.out.println("Informações pessoais removidas com sucesso.");
		} else {
			return;
		}
	}

	private void verHistoricoDeCompras(Comprador comprador) {
		List<Compra> historico = historicoDeComprasDao.verHistorico(comprador);

		if (historico.isEmpty()) {
			System.out.println("Ainda não foi realizada nenhuma compra.\n");
		} else {
			System.out.println("Histórico de compras\n");
			for (int i = 0; i < historico.size(); i++) {
				Compra compra = historico.get(i);
				System.out.println((i + 1) + ". " + compra);
			}

			System.out.println();

			System.out.print("Digite o número da compra para realizar a avaliação (ou 0 para voltar): ");
			int escolha = sc.nextInt();
			sc.nextLine();

			if (escolha == 0) {
				// Voltar para o menu
				return;
			} else if (escolha >= 1 && escolha <= historico.size()) {
				Compra compraEscolhida = historico.get(escolha - 1);

				System.out.println("Compra escolhida para realizar a avaliação: " + compraEscolhida);

				// Chame o método para realizar a avaliação da compra escolhida
				realizarAvaliacao(comprador, compraEscolhida);
			} else {
				System.out.println("Escolha inválida!");
			}
		}
	}

	private void realizarAvaliacao(Comprador comprador, Compra compraEscolhida) {
		System.out.print("Deseja avaliar a compra selecionada (S/N)? ");
		String resposta = sc.nextLine();

		if (resposta.equalsIgnoreCase("S")) {

			Pedido pedidoEscolhido = compraEscolhida.getPedido();
			Produto produtoEscolhido = pedidoEscolhido.getProduto();

			if (avaliacaoDao.existe(produtoEscolhido)) {
				System.out.println("Esse produto escolhido já foi avaliado!");
			} else {
				System.out.print("Digite o seu comentário para essa compra: ");
				String comentario = sc.nextLine();

				System.out.print("Digite a sua nota para essa compra: ");
				int nota = sc.nextInt();

				avaliacaoDao.adicionar(new Avaliacao(comentario, produtoEscolhido, nota));

				System.out.println("Avaliação realizada com sucesso!");
			}

		} else {
			System.out.println("Avaliação não foi realizada.");
		}
	}

}