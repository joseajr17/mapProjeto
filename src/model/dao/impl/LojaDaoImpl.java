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
import model.entities.Loja;

public class LojaDaoImpl implements LojaDao {

	public LojaDaoImpl() {

	}

	@Override
	public void cadastrar(Loja obj) {

		Gson gson = new Gson();

		try {
			// Verificar se o arquivo existe
			File arquivo = new File("C:\\Teste\\lojasExistentes.json");
			if (!arquivo.exists())
				arquivo.createNewFile();

			// Ler os compradores existentes do arquivo
			List<Loja> lojasExistentes = lerLojas();

			// Adicionar o novo comprador à lista
			lojasExistentes.add(obj);

			// Serializar a lista de compradores para JSON
			String json = gson.toJson(lojasExistentes);

			// Gravar o JSON no arquivo
			FileWriter writer = new FileWriter("C:\\Teste\\lojasExistentes.json");
			writer.write(json);
			writer.close();

			System.out.println("Loja cadastrada com sucesso!");
		} catch (IOException e) {
			System.out.println("Erro ao cadastrar a loja: " + e.getMessage());
		}
	}

	private List<Loja> lerLojas() {

		Gson gson = new Gson();

		List<Loja> lojas = new ArrayList<>();

		try {
			File arquivo = new File("C:\\Teste\\lojasExistentes.json");
			if (arquivo.exists()) {
				BufferedReader br = new BufferedReader(new FileReader("C:\\Teste\\lojasExistentes.json"));
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
	public Loja buscar(String cpfOUcnpj) {
		List<Loja> lojas = lerLojas();

	    for (Loja loja : lojas) {
	        if (loja.getCpfOUcnpj().equals(cpfOUcnpj)) {
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
	        	loja.setEmail(obj.getEmail());
	        	loja.setEndereco(obj.getEndereco());
	        	loja.setNome(obj.getNome());
	        	loja.setProdutos(obj.getProdutos());
	            loja.setSenha(obj.getSenha());
	            break;
	        }
	    }

	    salvarCompradores(lojas);

	}

	private void salvarCompradores(List<Loja> lojas) {
		Gson gson = new Gson();
		
		try {
	        FileWriter writer = new FileWriter("C:\\Teste\\lojasExistentes.json");
	        gson.toJson(lojas, writer);
	        writer.close();
	        System.out.println("Loja atualizada com sucesso!");
	    }
		catch (IOException e) {
	        System.out.println("Erro ao atualizar a loja: " + e.getMessage());
	    }
	}

	@Override
	public void remover(String cpfOUcnpj) {
		List<Loja> lojas = lerLojas();
		
		for (Iterator<Loja> iterator = lojas.iterator(); iterator.hasNext();) {
	        Loja loja = iterator.next();
	        if (loja.getCpfOUcnpj().equals(cpfOUcnpj.toString())) {
	            iterator.remove();
	            System.out.println("Loja removida com sucesso!");
	            break;
	        }
	    }
		salvarCompradores(lojas);
	}

	@Override
	public List<Loja> listarLojas() {
		List<Loja> lojas = lerLojas();
		return lojas;
	}

	@Override
	public boolean verificarCpfOuCnpjExiste(String cpfOUcnpj) {
		List<Loja> lojas = lerLojas();

	    for (Loja loja : lojas) {
	        if (loja.getCpfOUcnpj().equals(cpfOUcnpj)) {
	            return true;
	        }
	    }

	    return false;
	}

}
