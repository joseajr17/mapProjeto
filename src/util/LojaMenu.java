package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.LojaDao;
import model.dao.ProdutoDao;
import model.entities.Loja;
import model.entities.Produto;
import model.entities.ProdutoTipo;

public class LojaMenu {
	private static LojaDao lojaDao = DaoFactory.criarLojaDao();
	private static ProdutoDao produtoDao = DaoFactory.criarProdutoDao();
	private static Scanner sc = new Scanner(System.in);

	public void exibirMenuLoja(Loja loja) {
		int opcao;

		do {
			System.out.println("----- MENU DA LOJA -----");
			System.out.println("1. Adicionar produto");
			System.out.println("2. Atualizar informações do produto");
			System.out.println("3. Remover produto");
			System.out.println("4. Editar perfil");
			System.out.println("0. Sair");
			System.out.print("Escolha uma opção: ");
			opcao = sc.nextInt();
			sc.nextLine();

			switch (opcao) {
			case 1:
				adicionarProduto(loja);
				break;
			case 2:
				atualizarInformacoesProduto(loja);
				break;
			case 3:
				removerProduto(loja);
				break;
			case 4:
				editarPerfilLoja(loja);
				break;
			case 0:
				System.out.println("Saindo do menu da loja...");
				break;
			default:
				System.out.println("Opção inválida. Tente novamente.");
			}
		} while (opcao != 0);
	}

	private void adicionarProduto(Loja loja) {
		System.out.println("----- ADICIONAR PRODUTO -----");

		// Solicitar as informações do novo produto
		System.out.print("Nome do produto: ");
		String nome = sc.nextLine();

		System.out.print("Valor do produto em R$: ");
		Double valor = sc.nextDouble();

		System.out.print("Tipo do produto: ");
		ProdutoTipo tipo = ProdutoTipo.valueOf(sc.next().toUpperCase());

		System.out.print("Quantidade do produto: ");
		Integer quant = sc.nextInt();
		sc.nextLine();

		System.out.print("Marca do produto: ");
		String marca = sc.nextLine();

		System.out.print("Descrição do produto: ");
		String descricao = sc.nextLine();

		Produto produto = new Produto(nome, valor, tipo, quant, marca, descricao);
		List<Produto> produtos = new ArrayList<>();
		produtos.add(produto);
		loja.setProdutos(produtos);
		lojaDao.atualizar(loja);

		produtoDao.cadastrar(produto);

	}

	private void atualizarInformacoesProduto(Loja loja) {
		System.out.println("----- ATUALIZAR INFORMAÇÕES DO PRODUTO -----");

	    // Solicitar o nome do produto a ser atualizado
	    System.out.print("Digite o nome do produto a ser atualizado: ");
	    String nomeProduto = sc.nextLine();

	    // Verificar se o produto existe na lista de produtos da loja
	    Produto produtoAtualizado = null;
	    for (Produto produto : loja.getProdutos()) {
	        if (produto.getNome().equalsIgnoreCase(nomeProduto)) {
	            produtoAtualizado = produto;
	            break;
	        }
	    }

	    if (produtoAtualizado == null) {
	        System.out.println("Produto não encontrado na loja.");
	        return;
	    }

	    System.out.println("Digite as novas informações do produto (deixe em branco para manter os dados atuais):");

	    // Solicitar as novas informações do produto

	    System.out.print("Novo valor do produto em R$: ");
	    String novoValorStr = sc.nextLine();
	    if (!novoValorStr.isEmpty()) {
	        try {
	            double novoValor = Double.parseDouble(novoValorStr);
	            produtoAtualizado.setValor(novoValor);
	        } catch (NumberFormatException e) {
	            System.out.println("Valor inválido. O valor atual não será alterado.");
	        }
	    }

	    System.out.print("Novo tipo do produto: ");
	    String novoTipoStr = sc.nextLine();
	    if (!novoTipoStr.isEmpty()) {
	        try {
	            ProdutoTipo novoTipo = ProdutoTipo.valueOf(novoTipoStr.toUpperCase());
	            produtoAtualizado.setTipo(novoTipo);
	        } catch (IllegalArgumentException e) {
	            System.out.println("Tipo inválido. O tipo atual não será alterado.");
	        }
	    }

	    System.out.print("Nova quantidade do produto: ");
	    String novaQuantidadeStr = sc.nextLine();
	    if (!novaQuantidadeStr.isEmpty()) {
	        try {
	            int novaQuantidade = Integer.parseInt(novaQuantidadeStr);
	            produtoAtualizado.setQuantidade(novaQuantidade);
	        } catch (NumberFormatException e) {
	            System.out.println("Quantidade inválida. A quantidade atual não será alterada.");
	        }
	    }

	    System.out.print("Nova marca do produto: ");
	    String novaMarca = sc.nextLine();

	    System.out.print("Nova descrição do produto: ");
	    String novaDescricao = sc.nextLine();
	    
	    if (!novaMarca.isEmpty()) {
	        produtoAtualizado.setMarca(novaMarca);
	    }
	    
	    if (!novaDescricao.isEmpty()) {
	        produtoAtualizado.setDescricao(novaDescricao);
	    }

	    // Atualizar a loja no arquivo JSON
	    lojaDao.atualizar(loja);

	    // Atualizar o produto no arquivo JSON produtosExistentes
	    produtoDao.atualizar(produtoAtualizado);

	    System.out.println("Informações do produto atualizadas com sucesso.");	}

	private void removerProduto(Loja loja) {
	    System.out.println("----- REMOVER PRODUTO -----");

	    // Solicitar o nome do produto a ser removido
	    System.out.print("Digite o nome do produto a ser removido: ");
	    String nomeProduto = sc.nextLine();

	    // Verificar se o produto existe na lista de produtos da loja
	    Produto produtoRemovido = null;
	    for (Produto produto : loja.getProdutos()) {
	        if (produto.getNome().equalsIgnoreCase(nomeProduto)) {
	            produtoRemovido = produto;
	            break;
	        }
	    }

	    if (produtoRemovido == null) {
	        System.out.println("Produto não encontrado na loja.");
	        return;
	    }

	    // Remover o produto da lista de produtos da loja
	    loja.getProdutos().remove(produtoRemovido);

	    // Atualizar a loja no arquivo JSON
	    lojaDao.atualizar(loja);

	    // Remover o produto do arquivo JSON produtosExistentes
	    produtoDao.remover(nomeProduto);

	    System.out.println("Produto removido com sucesso.");
	}


	private void editarPerfilLoja(Loja loja) {
		System.out.print("Digite o seu CPF ou CNPJ: ");
	    String cpfOUcnpj = sc.nextLine();

	    // Verificar se o CPF corresponde ao comprador logado
	    if (!cpfOUcnpj.equals(loja.getCpfOUcnpj())) {
	        System.out.println("Você não digitou o seu CPF ou CNPJ corretamente.");
	        return;
	    }

	    System.out.println("CPF/CNPJ correto! Atualize as informações que desejar:");

	    System.out.print("Novo nome (deixe em branco para manter o atual): ");
	    String novoNome = sc.nextLine();

	    System.out.print("Novo email (deixe em branco para manter o atual): ");
	    String novoEmail = sc.nextLine();

	    System.out.print("Nova senha (deixe em branco para manter a atual): ");
	    String novaSenha = sc.nextLine();

	    System.out.print("Novo endereço (deixe em branco para manter o atual): ");
	    String novoEndereco = sc.nextLine();

	    // Verificar e atualizar as informações pessoais do comprador
	    if (!novoNome.isEmpty()) {
	        loja.setNome(novoNome);
	    }

	    if (!novoEmail.isEmpty()) {
	        loja.setEmail(novoEmail);
	    }

	    if (!novaSenha.isEmpty()) {
	        loja.setSenha(novaSenha);
	    }

	    if (!novoEndereco.isEmpty()) {
	        loja.setEndereco(novoEndereco);
	    }

	    // Atualizar o comprador no arquivo JSON
	    lojaDao.atualizar(loja);

	    System.out.println("Informações pessoais atualizadas com sucesso.");	
	}
}
