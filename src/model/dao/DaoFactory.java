package model.dao;

//import model.dao.impl.CarrinhoDeComprasDaoImpl;
import model.dao.impl.CompradorDaoImpl;
import model.dao.impl.LojaDaoImpl;
import model.dao.impl.ProdutoDaoImpl;

public class DaoFactory {

	public static CompradorDao criarCompradorDao() {
		return new CompradorDaoImpl();
	}
	
	public static LojaDao criarLojaDao() {
		return new LojaDaoImpl();
	}
	
	public static ProdutoDao criarProdutoDao() {
		return new ProdutoDaoImpl();
	}

	/*public static CarrinhoDeComprasDao criarCarrinhoDeComprasDao() {
		return new CarrinhoDeComprasDaoImpl();
	}*/
}
