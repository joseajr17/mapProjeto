package util;

import java.util.List;
import java.util.Scanner;

import model.dao.CompradorDao;
import model.dao.DaoFactory;
import model.dao.LojaDao;
import model.dao.ProdutoDao;
import model.entities.Comprador;
import model.entities.Loja;
import model.entities.Produto;

public class CompradorMenu {
	private static CompradorDao compradorDao = DaoFactory.criarCompradorDao();
	private static LojaDao lojaDao = DaoFactory.criarLojaDao();
	private static ProdutoDao produtoDao = DaoFactory.criarProdutoDao();
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
			System.out.println("6. Ir para a edição de perfil");
			System.out.println("7. Excluir perfil");
			System.out.println("0. Sair");
			System.out.print("Escolha uma opção: ");
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
				listarProdutosDeLojaEspecifica();
				break;
			case 6:
				editarPerfilComprador(comprador);
				break;
			case 7:
				excluirPerfilComprador(comprador);
			case 0:
				System.out.println("Saindo do menu do comprador...");
				break;
			default:
				System.out.println("Opção inválida. Tente novamente.");
			}
		} while (opcao != 0);
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
			System.out.println("Perfil do comprador excluído com sucesso.");
		} else {
			return;
		}
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
		
		////////ta exibindo apenas o primeiro produto do arquivo JSON/////////

		System.out.print("Digite o nome do produto: ");
		String nomeProduto = sc.nextLine();
		
		Produto produto = produtoDao.buscar(nomeProduto);
		
		if(produto!= null)
			System.out.println(produto.toString());
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
			for (Produto produto: produtos) {
				System.out.println(produto.getNome());
			}
			System.out.println();
		}
	}

	private void listarProdutosDeLojaEspecifica() {
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
	            for (Produto produto : produtosDaLoja) {
	                System.out.println(produto);
	            }
	        }
	    }
	}

	
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
	}
}
