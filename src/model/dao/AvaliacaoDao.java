package model.dao;

import java.util.List;

import model.entities.Avaliacao;
import model.entities.Loja;
import model.entities.Produto;

public interface AvaliacaoDao {
    void adicionar(Avaliacao avaliacao);
    boolean existe(Produto produtoEscolhido);
    List<Avaliacao> listar(Loja loja);
}
