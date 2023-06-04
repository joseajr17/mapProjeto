package util;

import java.util.ArrayList;
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
			System.out.println("5. Excluir perfil");
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
			case 5:
				excluirPerfilLoja(loja);
			case 0:
				System.out.println("Saindo do menu da loja...");
				break;
			default:
				System.out.println("Opção inválida. Tente novamente.");
			}
		} while (opcao != 0);
	}

	private void excluirPerfilLoja(Loja loja) {
		System.out.println("----- EXCLUIR PERFIL DA LOJA -----");

		System.out.print("Digite o CPF ou CNPJ da sua loja: ");
		String cpfOuCnpj = sc.nextLine();

		// Verificar se o CPF ou CNPJ corresponde ao da loja logada
		if (!cpfOuCnpj.equals(loja.getCpfOUcnpj())) {
			System.out.println("Você não digitou o CPF ou CNPJ correto.");
			return;
			
		}

		System.out.println("Tem certeza disso(DIGITE S para confirmar)?");

		String resp = sc.nextLine();

		if (resp.equalsIgnoreCase("s")) {
			// Remover a loja do arquivo JSON
			lojaDao.remover(cpfOuCnpj);
			System.out.println("Perfil da loja excluído com sucesso.");
		} else {
			return;
		}
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

	    Integer quant;
	    do{
            System.out.print("Quantidade do produto: ");
	    quant = sc.nextInt();
	    sc.nextLine();
        if(quant < 0){
            System.out.println("A quantidade de produto não pode ser negativa!");
        }
        } while(quant < 0);

	    System.out.print("Marca do produto: ");
	    String marca = sc.nextLine();

	    System.out.print("Descrição do produto: ");
	    String descricao = sc.nextLine();

	    Produto produto = new Produto(nome, valor, tipo, quant, marca, descricao);

	    // Verificar se a lista de produtos da loja está inicializada
	    if (loja.getProdutos() == null) {
	        loja.setProdutos(new ArrayList<>());
	    }

	    // Verificar se o produto já existe na loja antes de adicionar
	    if (!loja.getProdutos().contains(produto)) {
	        loja.getProdutos().add(produto);
	        lojaDao.atualizar(loja);

	        // cadastrar no arquivo JSON dos produtos
	        produtoDao.cadastrar(produto);
	    } else {
	        System.out.println("O produto já existe na loja.");
	    }
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

		System.out.println("Informações do produto atualizadas com sucesso.");
	}

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

	    // Confirmar a exclusão do produto
	    System.out.print("Tem certeza que deseja remover o produto? (S/N): ");
	    String confirmacao = sc.nextLine();

	    if (confirmacao.equalsIgnoreCase("S")) {
	        // Remover o produto da lista de produtos da loja
	        loja.getProdutos().remove(produtoRemovido);

	        // Atualizar a loja no arquivo JSON
	        lojaDao.atualizar(loja);

	        // Remover o produto do arquivo JSON produtosExistentes
	        produtoDao.remover(nomeProduto);

	        System.out.println("Produto removido com sucesso.");

	        // Verificar se a lista de produtos da loja está vazia e exibir mensagem apropriada
	        if (loja.getProdutos().isEmpty()) {
	            System.out.println("A lista de produtos da loja está vazia.");
	        } else {
	            System.out.println("Produtos restantes na loja:");
	            for (Produto produto : loja.getProdutos()) {
	                System.out.println(produto.getNome());
	            }
	        }
	    } else {
	        System.out.println("Exclusão cancelada.");
	    }
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
		loja.setNome(novoNome);

		System.out.print("Novo email (deixe em branco para manter o atual): ");
		String novoEmail = sc.nextLine();
		loja.setEmail(novoEmail);

		System.out.print("Nova senha (deixe em branco para manter a atual): ");
		String novaSenha = sc.nextLine();
		loja.setSenha(novaSenha);

		System.out.print("Novo endereço (deixe em branco para manter o atual): ");
		String novoEndereco = sc.nextLine();
		loja.setEndereco(novoEndereco);
		
		loja.setProdutos(loja.getProdutos());

		lojaDao.atualizar(loja);
	}
}
