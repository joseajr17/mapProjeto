package model.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.dao.ProdutoDao;
import model.entities.Produto;

public class ProdutoDaoImpl implements ProdutoDao{
	
	private static final String produtosPath = "C:\\Teste\\produtosExistentes.json";
	public ProdutoDaoImpl() {
		
	}

	@Override
	public void cadastrar(Produto obj) {
		Gson gson = new Gson();
		
		try {
			// Verificar se o arquivo existe
			File arquivo = new File(produtosPath);
			if (!arquivo.exists())
				arquivo.createNewFile();

			// Ler os compradores existentes do arquivo
			List<Produto> produtosExistentes = lerProdutos();

			// Adicionar o novo comprador à lista
			produtosExistentes.add(obj);

			// Serializar a lista de compradores para JSON
			String json = gson.toJson(produtosExistentes);

			// Gravar o JSON no arquivo
			FileWriter writer = new FileWriter(produtosPath);
			writer.write(json);
			writer.close();

			System.out.println("Produto cadastrado com sucesso!");
		}
		catch (IOException e) {
			System.out.println("Erro ao cadastrar o produto: " + e.getMessage());
		}
	}

	private List<Produto> lerProdutos() {
		
		Gson gson = new Gson();
		
		List<Produto> produtos = new ArrayList<>();

		try {
			File arquivo = new File(produtosPath);
			if (arquivo.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(produtosPath));
				Type listType = new TypeToken<ArrayList<Produto>>() {
				}.getType();
				produtos = gson.fromJson(br, listType);
				br.close();
			}
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo de produtos: " + e.getMessage());
		}
		
		if (produtos == null) {
	        produtos = new ArrayList<>();
	    }
		return produtos;
	}

	@Override
	public List<Produto> buscar(String nome) {
	    List<Produto> produtos = lerProdutos();
	    List<Produto> produtosEncontrados = new ArrayList<>();

	    for (Produto produto : produtos) {
	        if (produto.getNome().equals(nome)) {
	            produtosEncontrados.add(produto);
	        }
	    }
	    return produtosEncontrados;
	}

	@Override
	public void atualizar(Produto obj) {
		List<Produto> produtos = lerProdutos();

	    for (int i = 0; i < produtos.size(); i++) {
	        Produto produto = produtos.get(i);
	        if (produto.getNome().equals(obj.getNome())) {
	            produto.setDescricao(obj.getDescricao());
	            produto.setMarca(obj.getMarca());
	            produto.setQuantidade(obj.getQuantidade());
	            produto.setTipo(obj.getTipo());
	            produto.setValor(obj.getValor());
	            break;
	        }
	    }

	    salvarProdutos(produtos, true);
		
	}

	private void salvarProdutos(List<Produto> produtos, boolean acaoAtualizacao) {
		Gson gson = new Gson();

		try {
			FileWriter writer = new FileWriter(produtosPath);
			gson.toJson(produtos, writer);
			writer.close();
			if (acaoAtualizacao) {
				System.out.println("Produto atualizado com sucesso!");
			} else {
				System.out.println("Produto removido com sucesso!");
			}
		} catch (IOException e) {
			System.out.println("Erro ao atualizar/remover o produto: " + e.getMessage());
		}
	}

	@Override
	public void remover(Produto prod) {
		List<Produto> produtos = lerProdutos();

	    for (Iterator<Produto> iterator = produtos.iterator(); iterator.hasNext();) {
	        Produto produto = iterator.next();
	        if (produto.equals(prod)) {
	            iterator.remove();
	            System.out.println("Produto removido com sucesso!");
	            break;
	        }
	    }
	    salvarProdutos(produtos, false);
	}

	@Override
	public List<Produto> listarProdutos() {
		List<Produto> produtos = lerProdutos();
	    return produtos;
	}

}
