package Testes;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.dao.impl.ProdutoDaoImpl;
import model.entities.Produto;
import model.entities.ProdutoTipo;

public class ProdutoDaoImplTest {

    private ProdutoDaoImpl dao;
    
    @Before
    public void setUp() {
        dao = new ProdutoDaoImpl();
    }

    @After
    public void at() {
        dao.remover("Headphone");
    }
    
    @Test
    public void testCadastrar() {
        Produto produto = new Produto("Headphone", 29.99, ProdutoTipo.ELETRÔNICO, 10, "Stax", "Headphone preto");
        dao.cadastrar(produto);
        
        Produto cadastrado = dao.buscar("Headphone");
        assertNotNull(cadastrado);
        assertEquals("Headphone", cadastrado.getNome());
        assertEquals("Headphone preto", cadastrado.getDescricao());
        assertEquals("Stax", cadastrado.getMarca());
        assertEquals((Integer)10, cadastrado.getQuantidade());
        assertEquals(ProdutoTipo.ELETRÔNICO, cadastrado.getTipo());
        assertEquals(29.99, cadastrado.getValor(), 0.01);
    }
    
    @Test
    public void testBuscar() {
        Produto produto = dao.buscar("Camiseta");
        assertNull(produto);
        
        Produto novoProduto = new Produto("Headphone", 79.99, ProdutoTipo.ELETRÔNICO, 5, "Stax", "Headphone preto");
        dao.cadastrar(novoProduto);
        
        Produto encontrado = dao.buscar("Headphone");
        assertNotNull(encontrado);
        assertEquals("Headphone", encontrado.getNome());
        assertEquals("Headphone preto", encontrado.getDescricao());
        assertEquals("Stax", encontrado.getMarca());
        assertEquals((Integer)5, encontrado.getQuantidade());
        assertEquals(ProdutoTipo.ELETRÔNICO, encontrado.getTipo());
        assertEquals(79.99, encontrado.getValor(), 0.01);
    }
    
    @Test
    public void testAtualizar() {
        Produto produto = new Produto("Headphone", 29.99, ProdutoTipo.ELETRÔNICO, 5, "Stax", "Headphone preto");
        dao.cadastrar(produto);
        
        Produto atualizado = new Produto("Headphone", 39.99, ProdutoTipo.ELETRÔNICO, 10, "Stax", "Headphone preto");
        dao.atualizar(atualizado);
        
        Produto modificado = dao.buscar("Headphone");
        assertNotNull(modificado);
        assertEquals("Headphone", modificado.getNome());
        assertEquals("Headphone preto", modificado.getDescricao());
        assertEquals("Stax", modificado.getMarca());
        assertEquals((Integer)10, modificado.getQuantidade());
        assertEquals(ProdutoTipo.ELETRÔNICO, modificado.getTipo());
        assertEquals(39.99, modificado.getValor(), 0.01);
    }
    
    @Test
    public void testRemover() {
        Produto produto = new Produto("Headphone", 29.99, ProdutoTipo.ELETRÔNICO, 10, "Stax", "Headphone preto");
        dao.cadastrar(produto);
        
        dao.remover("Headphone");
        
        Produto naoEncontrado = dao.buscar("Headphone");
        assertNull(naoEncontrado);
    }
    
}