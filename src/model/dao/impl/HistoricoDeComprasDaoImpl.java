package model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import model.dao.CompradorDao;
import model.dao.DaoFactory;
import model.dao.HistoricoDeComprasDao;
import model.entities.Comprador;
import model.entities.Compra;

public class HistoricoDeComprasDaoImpl implements HistoricoDeComprasDao{

    private static CompradorDao compradorDao = DaoFactory.criarCompradorDao();

	@Override
	public void adicionar(Comprador comprador, Compra compra) {
		List<Compra> historicos = comprador.getHistoricoDeCompras();

		if (historicos == null) {
			historicos = new ArrayList<>();
		} 
		historicos.add(compra);

		comprador.setHistoricoDeCompras(historicos);

		compradorDao.atualizar(comprador);
		
	}

	@Override
	public List<Compra> verHistorico(Comprador comprador) {
		
		List<Compra> historico = comprador.getHistoricoDeCompras();
		if(historico == null){
			historico = new ArrayList<>();
		}
		return historico;
	}
  
}