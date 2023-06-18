package model.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.dao.CompradorDao;
import model.entities.Comprador;

public class CompradorDaoImpl implements CompradorDao {
	private static final String compradoresPath = "C:\\Teste\\compradoresExistentes.json";

	public CompradorDaoImpl() {

	}

	@Override
	public void cadastrar(Comprador obj) {

		Gson gson = new Gson();

		try {
			// Verificar se o arquivo existe
			File arquivo = new File(compradoresPath);
			if (!arquivo.exists())
				arquivo.createNewFile();

			// Ler os compradores existentes do arquivo
			List<Comprador> compradoresExistentes = lerCompradores();

			for (Comprador comprador : compradoresExistentes) {
				if (comprador.getCpf().equals(obj.getCpf()) || comprador.getEmail().equals(obj.getEmail())) {
					System.out.println("ERRO: Já existe um comprador com o CPF informado ou com o email informado.");
					return;
				}
			}

			// Adicionar o novo comprador à lista
			compradoresExistentes.add(obj);

			// Serializar a lista de compradores para JSON
			String json = gson.toJson(compradoresExistentes);

			// Gravar o JSON no arquivo
			FileWriter writer = new FileWriter(compradoresPath);
			writer.write(json);
			writer.close();
		}
		catch (IOException e) {
			System.out.println("Erro ao cadastrar o comprador: " + e.getMessage());
		}
	}

	private List<Comprador> lerCompradores() {

		Gson gson = new Gson();
		List<Comprador> compradores = new ArrayList<>();

		try {
			File arquivo = new File(compradoresPath);
			if (arquivo.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(compradoresPath));
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
	            if (!obj.getNome().isEmpty()) {
	                comprador.setNome(obj.getNome());
	            }
	            if (!obj.getEmail().isEmpty()) {
	                comprador.setEmail(obj.getEmail());
	            }
	            if (!obj.getSenha().isEmpty()) {
	                comprador.setSenha(obj.getSenha());
	            }
	            if (!obj.getEndereco().isEmpty()) {
	                comprador.setEndereco(obj.getEndereco());
	            }
	            if (!obj.getCarrinhoDeCompras().isEmpty()) {
	                comprador.setCarrinhoDeCompras(obj.getCarrinhoDeCompras());
	            }
	            if (!obj.getHistoricoDeCompras().isEmpty()) {
	                comprador.setHistoricoDeCompras(obj.getHistoricoDeCompras());
	            }
	            break;
	        }
	    }
	    salvarCompradores(compradores);
	}


	private void salvarCompradores(List<Comprador> compradores) {
		Gson gson = new Gson();

		try {
			FileWriter writer = new FileWriter(compradoresPath);
			gson.toJson(compradores, writer);
			writer.close();
		} catch (IOException e) {
			System.out.println("Erro ao atualizar/remover o comprador: " + e.getMessage());
		}
	}

	@Override
	public void remover(String cpf) {
		List<Comprador> compradores = lerCompradores();

		for (Iterator<Comprador> iterator = compradores.iterator(); iterator.hasNext();) {
			Comprador comprador = iterator.next();
			if (comprador.getCpf().equals(cpf.toString())) {
				iterator.remove();
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
}