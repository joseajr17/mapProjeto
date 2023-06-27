package model.dao;

import java.util.List;

import model.entities.Comentario;
import model.entities.Loja;

public interface ComentarioDao {
    void adicionar(Comentario comentario);
    List<Comentario> listar(Loja loja);
}
