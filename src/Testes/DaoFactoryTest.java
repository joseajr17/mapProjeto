package Testes;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import model.dao.CarrinhoDeComprasDao;
import model.dao.CompradorDao;
import model.dao.DaoFactory;
import model.dao.HistoricoDeComprasDao;
import model.dao.LojaDao;
import model.dao.ProdutoDao;

public class DaoFactoryTest {
    
    @Test
    public void testCriarCompradorDao() {
        CompradorDao compradorDao = DaoFactory.criarCompradorDao();
        assertNotNull(compradorDao);
    }

    @Test
    public void testCriarLojaDao() {
        LojaDao lojaDao = DaoFactory.criarLojaDao();
        assertNotNull(lojaDao);
    }

    @Test
    public void testCriarProdutoDao() {
        ProdutoDao produtoDao = DaoFactory.criarProdutoDao();
        assertNotNull(produtoDao);
    }

    @Test
    public void testCriarCarrinhoDeComprasDao() {
        CarrinhoDeComprasDao carrinhoDao = DaoFactory.criarCarrinhoDeComprasDao();
        assertNotNull(carrinhoDao);
    }

    @Test
    public void testCriarHistoricoDeComprasDao() {
        HistoricoDeComprasDao historicoDao = DaoFactory.criarHistoricoDeComprasDao();
        assertNotNull(historicoDao);
    }
}
