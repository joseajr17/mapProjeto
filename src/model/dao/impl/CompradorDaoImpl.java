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

import model.dao.CompradorDao;
import model.entities.Comprador;

public class CompradorDaoImpl implements CompradorDao {

	public CompradorDaoImpl() {

	}

	@Override
	public void cadastrar(Comprador obj) {

		Gson gson = new Gson();

		try {
			// Verificar se o arquivo existe
			File arquivo = new File("C:\\Teste\\compradoresExistentes.json");
			if (!arquivo.exists())
				arquivo.createNewFile();

			// Ler os compradores existentes do arquivo
			List<Comprador> compradoresExistentes = lerCompradores();

			// Adicionar o novo comprador à lista
			compradoresExistentes.add(obj);

			// Serializar a lista de compradores para JSON
			String json = gson.toJson(compradoresExistentes);

			// Gravar o JSON no arquivo
			FileWriter writer = new FileWriter("C:\\Teste\\compradoresExistentes.json");
			writer.write(json);
			writer.close();

			System.out.println("Comprador cadastrado com sucesso!");
		}

		catch (IOException e) {
			System.out.println("Erro ao cadastrar o comprador: " + e.getMessage());
		}

	}

	private List<Comprador> lerCompradores() {

		Gson gson = new Gson();
		List<Comprador> compradores = new ArrayList<>();

		try {
			File arquivo = new File("C:\\Teste\\compradoresExistentes.json");
			if (arquivo.exists()) {
				BufferedReader br = new BufferedReader(new FileReader("C:\\Teste\\compradoresExistentes.json"));
				Type listType = new TypeToken<ArrayList<Comprador>>() {
				}.getType();
				compradores = gson.fromJson(br, listType);
				br.close();
			}
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo de compradores: " + e.getMessage());
		}
		
		if (compradores == null) {
	        compradores = new ArrayList<>();
	    }

		return compradores;
	}

	@Override
	public Comprador buscar(String email) {
		List<Comprador> compradores = lerCompradores();

	    for (Comprador comprador : compradores) {
	        if (comprador.getEmail().equals(email)) {
	            return comprador;
	        }
	    }

	    return null;
	   }

	@Override
	public void atualizar(Comprador obj) {
		List<Comprador> compradores = lerCompradores();

	    for (int i = 0; i < compradores.size(); i++) {
	        Comprador comprador = compradores.get(i);
	        if (comprador.getCpf().equals(obj.getCpf())) {
	            comprador.setNome(obj.getNome());
	            comprador.setEmail(obj.getEmail());
	            comprador.setSenha(obj.getSenha());
	            comprador.setEndereco(obj.getEndereco());
	            break;
	        }
	    }

	    salvarCompradores(compradores);

	}

	private void salvarCompradores(List<Comprador> compradores) {
		Gson gson = new Gson();

	    try {
	        FileWriter writer = new FileWriter("C:\\Teste\\compradoresExistentes.json");
	        gson.toJson(compradores, writer);
	        writer.close();
	        System.out.println("Comprador atualizado com sucesso!");
	    } catch (IOException e) {
	        System.out.println("Erro ao atualizar o comprador: " + e.getMessage());
	    }
		
	}

	@Override
	public void remover(String cpf) {
		List<Comprador> compradores = lerCompradores();

	    for (Iterator<Comprador> iterator = compradores.iterator(); iterator.hasNext();) {
	        Comprador comprador = iterator.next();
	        if (comprador.getCpf().equals(cpf.toString())) {
	            iterator.remove();
	            System.out.println("Comprador removido com sucesso!");
	            break;
	        }
	    }

	    salvarCompradores(compradores);

	}

	@Override
	public List<Comprador> listarCompradores() {
		List<Comprador> compradores = lerCompradores();
	    return compradores;
	}

	@Override
	public boolean verificarCpfExiste(String cpf) {
		List<Comprador> compradores = lerCompradores();

	    for (Comprador comprador : compradores) {
	        if (comprador.getCpf().equals(cpf)) {
	            return true;
	        }
	    }

	    return false;
	}

}
