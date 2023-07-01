package model.dao;

import java.util.List;

import model.entities.Avaliacao;
import model.entities.Loja;

public interface AvaliacaoDao {
    void adicionar(Avaliacao avaliacao);
    List<Avaliacao> listar(Loja loja);
    void remover(Loja loja);
}