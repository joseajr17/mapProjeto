package Testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.StoreNotFoundException;
import model.dao.impl.CarrinhoDeComprasDaoImpl;
import model.dao.impl.CompradorDaoImpl;
import model.dao.impl.HistoricoDeComprasDaoImpl;
import model.dao.impl.LojaDaoImpl;
import model.dao.impl.ProdutoDaoImpl;
import model.entities.Comprador;
import model.entities.Loja;
import model.entities.Produto;
import model.entities.ProdutoTipo;

public class CarrinhoDeComprasDaoImplTest {
    
    private CompradorDaoImpl dao;
    private CarrinhoDeComprasDaoImpl carrinho;

    @Before
    public void setUp() {
        dao = new CompradorDaoImpl();
        carrinho = new CarrinhoDeComprasDaoImpl();
    }

    @After
    public void remove(){
        dao.remover("12345678901teste");
    }

    @Test
    public void testAdicionar(){
        Comprador comprador = new Comprador("Teste", "Teste@gmail.com", "123456teste", "12345678901teste", "Rua Teste");
        dao.cadastrar(comprador);
        assertTrue(comprador.getCarrinhoDeCompras().isEmpty());
        carrinho.adicionar(comprador, new Produto("Headphone", 29.99, ProdutoTipo.ELETRONICO, 10, "Stax", "Headphone preto", "teste3@gmail.com"));
        assertFalse(comprador.getCarrinhoDeCompras().isEmpty());
    }

    @Test
    public void testRemover(){
        Comprador comprador = new Comprador("Teste", "Teste@gmail.com", "123456teste", "12345678901teste", "Rua Teste");
        dao.cadastrar(comprador);
        assertTrue(comprador.getCarrinhoDeCompras().isEmpty());
        Produto produto = new Produto("Headphone", 29.99, ProdutoTipo.ELETRONICO, 10, "Stax", "Headphone preto", "teste32gmail.com");
        carrinho.adicionar(comprador, produto);
        assertFalse(comprador.getCarrinhoDeCompras().isEmpty());
        carrinho.remover(comprador, produto);
        assertTrue(dao.buscar(comprador.getEmail()).getCarrinhoDeCompras().isEmpty());
        assertTrue(carrinho.listarProdutos(comprador).isEmpty());
    }

    @Test
    public void testListarProdutos(){
        Comprador comprador = new Comprador("Teste", "Teste@gmail.com", "123456teste", "12345678901teste", "Rua Teste");
        dao.cadastrar(comprador);
         Produto produto1 = new Produto("Headphone", 29.99, ProdutoTipo.ELETRONICO, 10, "Stax", "Headphone preto", "teste3@gmail.com");
        carrinho.adicionar(comprador, produto1);
        assertFalse(carrinho.listarProdutos(comprador).isEmpty());
        assertEquals(1, carrinho.listarProdutos(comprador).size());
        Produto produto2 = new Produto("Os mentirosos", 45.99, ProdutoTipo.LIVRO, 4, "Editora Semear", "Um livro ótimo", "teste4@gmail.com");
        carrinho.adicionar(comprador, produto2);
        assertEquals(2, carrinho.listarProdutos(comprador).size());
        assertEquals(produto2, carrinho.listarProdutos(comprador).get(1));
    }

    @Test
    public void testComprar() throws StoreNotFoundException{
        Comprador comprador = new Comprador("Teste", "Teste@gmail.com", "123456teste", "12345678901teste", "Rua Teste");
        dao.cadastrar(comprador);
        Produto produto1 = new Produto("Headphone", 29.99, ProdutoTipo.ELETRONICO, 10, "Stax", "Headphone preto", "teste3@gmail.com");
        Loja loja = new Loja("Loja de fones", "teste3@gmail.com", "123teste", "teste123456", "Rua dos fones", new ArrayList<>());
        LojaDaoImpl lojaDao = new LojaDaoImpl();
        lojaDao.cadastrar(loja);
        loja.getProdutos().add(produto1);
	    lojaDao.atualizar(loja);
        ProdutoDaoImpl produtoDao = new ProdutoDaoImpl();
	    produtoDao.cadastrar(produto1);
        carrinho.adicionar(comprador, produto1);
        carrinho.comprar(comprador, produto1, 3);
        HistoricoDeComprasDaoImpl historico = new HistoricoDeComprasDaoImpl();
        assertFalse(historico.verHistorico(dao.buscar(comprador.getEmail())).isEmpty());
        assertEquals(1, historico.verHistorico(dao.buscar(comprador.getEmail())).size());
        int quantidade = lojaDao.buscarPeloEmail(produto1.getEmailLoja()).getProdutos().get(0).getQuantidade();
        assertEquals(7, quantidade);
        quantidade = produtoDao.buscar("Headphone").get(0).getQuantidade();
        assertEquals(7, quantidade);
        assertEquals("Produto comprado com sucesso. Compra registrada.", carrinho.comprar(comprador, produto1, quantidade));
        assertThrows(IndexOutOfBoundsException.class, () -> {lojaDao.buscarPeloEmail(produto1.getEmailLoja()).getProdutos().get(0).getQuantidade();});
        // quantidade = lojaDao.buscarPeloEmail(produto1.getEmailLoja()).getProdutos().get(0).getQuantidade();
       // assertEquals(0, quantidade);
       //quantidade = produtoDao.buscar("Headphone").get(0).getQuantidade();
       assertThrows(IndexOutOfBoundsException.class, () -> {produtoDao.buscar("Headphone").get(0).getQuantidade();});
        //assertEquals(0, quantidade);

        //produtoDao.remover(produtoDao.buscar("Headphone").get(0));
        lojaDao.remover("teste123456");
    }

    @Test
    public void testComprarException() throws StoreNotFoundException{
        Comprador comprador = new Comprador("Teste", "Teste@gmail.com", "123456teste", "12345678901teste", "Rua Teste");
        dao.cadastrar(comprador);
        Produto produto1 = new Produto("Headphone", 29.99, ProdutoTipo.ELETRONICO, 10, "Stax", "Headphone preto", "teste5@gmail.com");
        Loja loja = new Loja("Loja de fones", "teste3@gmail.com", "123teste", "teste123456", "Rua dos fones", new ArrayList<>());
        LojaDaoImpl lojaDao = new LojaDaoImpl();
        lojaDao.cadastrar(loja);
        loja.getProdutos().add(produto1);
	    lojaDao.atualizar(loja);
        ProdutoDaoImpl produtoDao = new ProdutoDaoImpl();
	    produtoDao.cadastrar(produto1);
        assertThrows(StoreNotFoundException.class, () -> {
            carrinho.comprar(comprador, produto1, 4);
        });
        produtoDao.remover(produto1);
        lojaDao.remover("teste123456");
    }
}