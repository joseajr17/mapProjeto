package model.dao;

import java.util.List;

import model.entities.Comprador;
import model.entities.Compra;

public interface HistoricoDeComprasDao {
    void adicionar(Comprador comprador, Compra compra);
    List<Compra> verHistorico(Comprador comprador);
    String atualizar(Comprador comprador, Compra compra);
}
