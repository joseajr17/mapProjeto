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

import model.dao.LojaDao;
import model.dao.ProdutoDao;
import model.entities.Loja;
import model.entities.Produto;

public class LojaDaoImpl implements LojaDao {

	private static final String lojasPath = "C:\\Teste\\lojasExistentes.json";

	public LojaDaoImpl() {

	}

	@Override
	public void cadastrar(Loja obj) {

		Gson gson = new Gson();

		try {
			// Verificar se o arquivo existe
			File arquivo = new File(lojasPath);
			if (!arquivo.exists())
				arquivo.createNewFile();

			// Ler as lojas existentes do arquivo
			List<Loja> lojasExistentes = lerLojas();

			for (Loja loja : lojasExistentes) {
				if (loja.getCpfOUcnpj().equals(obj.getCpfOUcnpj()) || loja.getEmail().equals(obj.getEmail())) {
					System.out.println("ERRO: Já existe uma loja com o CPF/CNPJ informado ou com o email informado.");
					return;
				}
			}

			// Adicionar o novo comprador à lista
			lojasExistentes.add(obj);

			// Serializar a lista de compradores para JSON
			String json = gson.toJson(lojasExistentes);

			// Gravar o JSON no arquivo
			FileWriter writer = new FileWriter(lojasPath);
			writer.write(json);
			writer.close();

		} catch (IOException e) {
			System.out.println("Erro ao cadastrar a loja: " + e.getMessage());
		}
	}

	private List<Loja> lerLojas() {

		Gson gson = new Gson();

		List<Loja> lojas = new ArrayList<>();

		try {
			File arquivo = new File(lojasPath);
			if (arquivo.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(lojasPath));
				Type listType = new TypeToken<ArrayList<Loja>>() {
				}.getType();
				lojas = gson.fromJson(br, listType);
				br.close();
			}
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo de lojas: " + e.getMessage());
		}

		if (lojas == null) {
			lojas = new ArrayList<>();
		}

		return lojas;

	}

	@Override
	public Loja buscarPeloNome(String nome) {
		List<Loja> lojas = lerLojas();

	    for (Loja loja : lojas) {
	        if (loja.getNome().equals(nome)) {
	            return loja;
	        }
	    }

	    return null;
	   }
	
	@Override
	public Loja buscarPeloEmail(String email) {
		List<Loja> lojas = lerLojas();

	    for (Loja loja : lojas) {
	        if (loja.getEmail().equals(email)) {
	            return loja;
	        }
	    }

	    return null;
	   }

	@Override
	public void atualizar(Loja obj) {
		
		List<Loja> lojas = lerLojas();

	    for (int i = 0; i < lojas.size(); i++) {
	        Loja loja = lojas.get(i);
	        if (loja.getCpfOUcnpj().equals(obj.getCpfOUcnpj())) {
	        	if (!obj.getNome().isEmpty()) {
	        		loja.setNome(obj.getNome());
	    	    }
	        	if (!obj.getEmail().isEmpty()) {
	        		loja.setEmail(obj.getEmail());
	    	    }
	        	if (!obj.getEndereco().isEmpty()) {
	        		loja.setEndereco(obj.getEndereco());
	    	    }
	        	if (obj.getProdutos() != null) {
	        		loja.setProdutos(obj.getProdutos());
	    	    }
	        	if (!obj.getSenha().isEmpty()) {
	        		loja.setSenha(obj.getSenha());
	    	    }
				
	            break;
	        }
	    }

	    salvarLojas(lojas, true);

	}

	private void salvarLojas(List<Loja> lojas, boolean acaoAtualizacao) {
		Gson gson = new Gson();

		try {
			FileWriter writer = new FileWriter(lojasPath);
			gson.toJson(lojas, writer);
			writer.close();
			if (acaoAtualizacao) {

			} else {

			}
		} catch (IOException e) {
			System.out.println("Erro ao atualizar/remover a loja: " + e.getMessage());
		}
	}


	private void removerProdutos(Loja loja){
		ProdutoDao produtoDao = new ProdutoDaoImpl();
		if (loja.getProdutos() != null) {
			for (Produto produto : loja.getProdutos()) {
				produtoDao.remover(produto);
			}
		}
	}

	@Override
	public void remover(String cpfOUcnpj) {
		List<Loja> lojas = lerLojas();
		
		for (Iterator<Loja> iterator = lojas.iterator(); iterator.hasNext();) {
	        Loja loja = iterator.next();
	        if (loja.getCpfOUcnpj().equals(cpfOUcnpj.toString())) {
	            iterator.remove();
				removerProdutos(loja);
	            break;
	        }
	    }
		salvarLojas(lojas, false);
	}

	@Override
	public List<Loja> listarLojas() {
		List<Loja> lojas = lerLojas();
		return lojas;
	}
}