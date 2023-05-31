package model.dao;

import java.util.List;

import model.entities.Loja;

public interface LojaDao {
	
	void cadastrar(Loja obj);
	//o de exibir seria o toString???
	Loja buscarPeloNome(String nome);
	Loja buscarPeloEmail(String email);
	void atualizar(Loja obj);
	void remover(String cpfOUcnpj);
	List<Loja> listarLojas();
	
}
