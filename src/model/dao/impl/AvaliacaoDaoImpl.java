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

import model.dao.AvaliacaoDao;
import model.entities.Avaliacao;
import model.entities.Loja;

public class AvaliacaoDaoImpl implements AvaliacaoDao{

    private static final String avaliacoesPath = "C:\\Teste\\AvaliacoesExistentes.json";
	public AvaliacaoDaoImpl() {
		
	}

		@Override
	public void adicionar(Avaliacao avaliacao) {
		Gson gson = new Gson();

		try {
			// Verificar se o arquivo existe
			File arquivo = new File(avaliacoesPath);
			if (!arquivo.exists())
				arquivo.createNewFile();

			// Ler as avaliacoes existentes do arquivo
			List<Avaliacao> avaliacoesExistentes = lerAvaliacoes();

			// Adicionar a nova avaliacao à lista
			avaliacoesExistentes.add(avaliacao);

			// Serializar a lista de avaliacoes para JSON
			String json = gson.toJson(avaliacoesExistentes);

			// Gravar o JSON no arquivo
			FileWriter writer = new FileWriter(avaliacoesPath);
			writer.write(json);
			writer.close();

		} catch (IOException e) {
			System.out.println("Erro ao adicionar a avaliacao: " + e.getMessage());
		}
	}

	private List<Avaliacao> lerAvaliacoes() {

		Gson gson = new Gson();

		List<Avaliacao> avaliacoes = new ArrayList<>();

		try {
			File arquivo = new File(avaliacoesPath);
			if (arquivo.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(avaliacoesPath));
				Type listType = new TypeToken<ArrayList<Avaliacao>>() {
				}.getType();
				avaliacoes = gson.fromJson(br, listType);
				br.close();
			}
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo de avaliacoes: " + e.getMessage());
		}

		if (avaliacoes == null) {
			avaliacoes = new ArrayList<>();
		}

		return avaliacoes;
	}

	private List<Avaliacao> buscar(String loja) {
		List<Avaliacao> avaliacoes = lerAvaliacoes();
		List<Avaliacao> avaliacoesEncontradas = new ArrayList<>();

		for (Avaliacao avaliacao : avaliacoes) {
			if (avaliacao.getKeyLoja().equals(loja)) {
				avaliacoesEncontradas.add(avaliacao);
			}
		}
		return avaliacoesEncontradas;
	}

	@Override
	public List<Avaliacao> listar(Loja loja) {
		return buscar(loja.getEmail());
	}

	// Deleção em cascata da loja
	@Override
	public void remover(Loja loja){
		List <Avaliacao> avaliacoes = lerAvaliacoes();
		String emailLoja = loja.getEmail();

		for (Iterator<Avaliacao> iterator = avaliacoes.iterator(); iterator.hasNext();) {
	        Avaliacao coment = iterator.next();
	        if (coment.getKeyLoja().equals(emailLoja)) {
	            iterator.remove();
	        }
	    }
		salvarAvaliacoes(avaliacoes);
	}

	private void salvarAvaliacoes(List<Avaliacao> avaliacoes) {
		Gson gson = new Gson();

		try {
			FileWriter writer = new FileWriter(avaliacoesPath);
			gson.toJson(avaliacoes, writer);
			writer.close();
			
		} catch (IOException e) {
			System.out.println("Erro ao remover as avaliações da loja: " + e.getMessage());
		}
	}
}