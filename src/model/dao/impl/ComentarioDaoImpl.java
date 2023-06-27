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

import model.dao.ComentarioDao;
import model.entities.Comentario;
import model.entities.Loja;

public class ComentarioDaoImpl implements ComentarioDao{

    private static final String comentariosPath = "C:\\Teste\\ComentariosExistentes.json";
	public ComentarioDaoImpl() {
		
	}

	@Override
	public void adicionar(Comentario comentario) {
		Gson gson = new Gson();
		
		try {
			// Verificar se o arquivo existe
			File arquivo = new File(comentariosPath);
			if (!arquivo.exists())
				arquivo.createNewFile();

			// Ler os comentários existentes do arquivo
			List<Comentario> comentariosExistentes = lerComentarios();

			// Adicionar o novo comentário à lista
			comentariosExistentes.add(comentario);

			// Serializar a lista de comentários para JSON
			String json = gson.toJson(comentariosExistentes);

			// Gravar o JSON no arquivo
			FileWriter writer = new FileWriter(comentariosPath);
			writer.write(json);
			writer.close();

			
		}
		catch (IOException e) {
			System.out.println("Erro ao adicionar o comentário: " + e.getMessage());
		}
	}

	private List<Comentario> lerComentarios() {
		
		Gson gson = new Gson();
		
		List<Comentario> comentarios = new ArrayList<>();

		try {
			File arquivo = new File(comentariosPath);
			if (arquivo.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(comentariosPath));
				Type listType = new TypeToken<ArrayList<Comentario>>() {
				}.getType();
				comentarios = gson.fromJson(br, listType);
				br.close();
			}
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo de comentários: " + e.getMessage());
		}
		
		if (comentarios == null) {
	        comentarios = new ArrayList<>();
	    }
	
        return comentarios;
	}


	private List<Comentario> buscar(String loja) {
	    List<Comentario> comentarios = lerComentarios();
	    List<Comentario> comentariosEncontrados = new ArrayList<>();

	    for (Comentario comentario : comentarios) {
	        if (comentario.getKeyLoja().equals(loja)) {
	            comentariosEncontrados.add(comentario);
	        }
	    }
	    return comentariosEncontrados;
	}


	@Override
    public List<Comentario> listar(Loja loja) {
        return buscar(loja.getEmail());
    }

	public String remover(Comentario comentario){
		List <Comentario> comentarios = lerComentarios();

		for (Iterator<Comentario> iterator = comentarios.iterator(); iterator.hasNext();) {
	        Comentario coment = iterator.next();
	        if (coment.equals(comentario)) {
	            iterator.remove();
				salvarComentarios(comentarios);
	            return "Comentário removido com sucesso!";
	
	        }
	    }
		return "Comentário não encontrado";
	}

	private void salvarComentarios(List<Comentario> comentarios) {
		Gson gson = new Gson();

		try {
			FileWriter writer = new FileWriter(comentariosPath);
			gson.toJson(comentarios, writer);
			writer.close();
			
		} catch (IOException e) {
			System.out.println("Erro ao remover o comentário: " + e.getMessage());
		}
	}
}