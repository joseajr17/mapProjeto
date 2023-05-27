package model.dao;

import java.util.List;

import model.entities.Loja;

public interface LojaDao {
	
	void cadastrar(Loja obj);
	//o de exibir seria o toString???
	Loja buscar(Integer cpfOUcnpj);
	void atualizar(Loja obj);
	void remover(Integer cpfOUcnpj);
	List<Loja> listarLojas();
	

}
