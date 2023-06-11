package Testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.dao.impl.CompradorDaoImpl;
import model.dao.impl.HistoricoDeComprasDaoImpl;
import model.entities.Compra;
import model.entities.Comprador;
import model.entities.Pedido;
import model.entities.Produto;
import model.entities.ProdutoTipo;

public class HistoricoDeComprasDaoImplTest {
    private CompradorDaoImpl dao;
    private HistoricoDeComprasDaoImpl historico;

    @Before
    public void setUp() {
        dao = new CompradorDaoImpl();
        historico = new HistoricoDeComprasDaoImpl();
    }

    @Test
    public void testAdicionar(){
        Comprador comprador = new Comprador("Teste", "Teste@gmail.com", "123456teste", "12345678901teste", "Rua Teste");
        dao.cadastrar(comprador);
        assertTrue(comprador.getHistoricoDeCompras().isEmpty());
        historico.adicionar(comprador, new Compra(new Pedido(
            new Produto("Headphone", 29.99, ProdutoTipo.ELETRÔNICO, 10, "Stax", "Headphone preto", "teste3@gmail.com"), 3)));
        assertFalse(comprador.getHistoricoDeCompras().isEmpty());
        dao.remover("12345678901teste");
    }

    @Test
    public void testVerHistorico(){
        Comprador comprador = new Comprador("Teste", "Teste@gmail.com", "123456teste", "12345678901teste", "Rua Teste");
        dao.cadastrar(comprador);
        assertTrue(comprador.getHistoricoDeCompras().isEmpty());
        Compra compra = new Compra(new Pedido(new Produto("Headphone", 29.99, ProdutoTipo.ELETRÔNICO, 10, "Stax", "Headphone preto", "teste3@gmail.com"), 3));
        historico.adicionar(comprador, compra);
        assertFalse(comprador.getHistoricoDeCompras().isEmpty());
        assertEquals(historico.verHistorico(comprador).size(), 1);
        assertEquals(historico.verHistorico(comprador).get(0), compra);
        dao.remover("12345678901teste");
    }
}
