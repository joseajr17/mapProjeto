package model.dao;

import java.util.List;

import model.entities.Comprador;

public interface CompradorDao {
	
	void cadastrar(Comprador obj);
	//o de exibir seria o toString???
	Comprador buscar(String cpf);
	void atualizar(Comprador obj);
	void remover(String cpf);
	List<Comprador> listarCompradores();
	
	
}
