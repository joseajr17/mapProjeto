package model.dao;

import java.util.List;

import model.entities.Comprador;

public interface CompradorDao {
	
	void cadastrar(Comprador obj);
	Comprador buscar(String email);
	void atualizar(Comprador obj);
	void remover(String cpf);
	List<Comprador> listarCompradores();
}