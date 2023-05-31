package util;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.dao.CompradorDao;
import model.dao.DaoFactory;
import model.dao.LojaDao;
import model.entities.Comprador;
import model.entities.Loja;
import model.entities.Produto;

public class CompradorMenu {
	private static CompradorDao compradorDao = DaoFactory.criarCompradorDao();
	private static LojaDao lojaDao = DaoFactory.criarLojaDao();
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
			case 0:
				System.out.println("Saindo do menu do comprador...");
				break;
			default:
				System.out.println("Opção inválida. Tente novamente.");
			}
		} while (opcao != 0);
	}

	private static List<Loja> getLojas() {
		try (Reader reader = new FileReader("C:\\Teste\\lojasExistentes.json")) {
			Gson gson = new Gson();
			Type listType = new TypeToken<List<Loja>>() {
			}.getType();
			List<Loja> lojas = gson.fromJson(reader, listType);
			return lojas;
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>(); // Retornar uma lista vazia em caso de erro
		}
	}

	private void buscarLoja() {
		List<Loja> lojas = getLojas();

		System.out.print("Digite o nome da loja: ");
		String nomeLoja = sc.nextLine();

		// Realizar a busca na lista de lojas
		List<Loja> lojasEncontradas = new ArrayList<>();
		for (Loja loja : lojas) {
			if (loja.getNome().equalsIgnoreCase(nomeLoja)) {
				lojasEncontradas.add(loja);
			}
		}

		// Exibir os resultados da busca
		if (lojasEncontradas.isEmpty()) {
			System.out.println("Nenhuma loja encontrada com o nome informado.");
		} else {
			System.out.println("Lojas encontradas:");
			for (Loja loja : lojasEncontradas) {
				System.out.println("Nome: " + loja.getNome());
				System.out.println("Endereço: " + loja.getEndereco());
				System.out.println("Email: " + loja.getEmail());
				System.out.println();
			}
		}
	}

	private void listarTodasAsLojas() {
		List<Loja> lojas = getLojas();

		if (lojas.isEmpty()) {
			System.out.println("Não há lojas cadastradas.");
		} else {
			System.out.println("Lista de todas as lojas:");
			for (Loja loja : lojas) {
				System.out.println(loja.getNome());
			}
		}
	}

	private static List<Produto> getProdutos() {
		try (Reader reader = new FileReader("C:\\Teste\\produtosExistentes.json")) {
			Gson gson = new Gson();
			Type listType = new TypeToken<List<Produto>>() {
			}.getType();
			List<Produto> produtos = gson.fromJson(reader, listType);
			return produtos;
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>(); // Retornar uma lista vazia em caso de erro
		}
	}

	private void buscarProduto() {
		List<Produto> produtos = getProdutos();

		System.out.print("Digite o nome do produto: ");
		String nomeProduto = sc.nextLine();

		// Realizar a busca na lista de produtos
		List<Produto> produtosEncontrados = new ArrayList<>();
		for (Produto produto : produtos) {
			if (produto.getNome().equalsIgnoreCase(nomeProduto)) {
				produtosEncontrados.add(produto);
			}
		}

		// Exibir os resultados da busca
		if (produtosEncontrados.isEmpty()) {
			System.out.println("Nenhum produto encontrado com o nome informado.");
		} else {
			System.out.println("Produtos encontrados:");
			for (Produto produto : produtosEncontrados) {
				System.out.println(produto.toString());
				System.out.println();
				
			}
		}
	}

	private void listarTodosOsProdutos() {
		List<Produto> produtos = getProdutos();

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
	    Loja lojaEncontrada = lojaDao.buscar(nomeLoja);

	    // Exibir os resultados da busca
	    if (lojaEncontrada == null) {
	        System.out.println("Loja não encontrada.");
	    } else {
	        List<Produto> produtosDaLoja = lojaEncontrada.getProdutos();
	        if (produtosDaLoja.isEmpty()) {
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

	    System.out.print("Novo email (deixe em branco para manter o atual): ");
	    String novoEmail = sc.nextLine();

	    System.out.print("Nova senha (deixe em branco para manter a atual): ");
	    String novaSenha = sc.nextLine();

	    System.out.print("Novo endereço (deixe em branco para manter o atual): ");
	    String novoEndereco = sc.nextLine();

	    // Verificar e atualizar as informações pessoais do comprador
	    if (!novoNome.isEmpty()) {
	        comprador.setNome(novoNome);
	    }

	    if (!novoEmail.isEmpty()) {
	        comprador.setEmail(novoEmail);
	    }

	    if (!novaSenha.isEmpty()) {
	        comprador.setSenha(novaSenha);
	    }

	    if (!novoEndereco.isEmpty()) {
	        comprador.setEndereco(novoEndereco);
	    }

	    // Atualizar o comprador no arquivo JSON
	    compradorDao.atualizar(comprador);

	    System.out.println("Informações pessoais atualizadas com sucesso.");	
	}
}
