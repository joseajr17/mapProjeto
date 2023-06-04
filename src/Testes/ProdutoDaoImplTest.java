package Testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.List;

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
    
    @Test
    public void testCadastrar() {
        Produto produto = new Produto("Headphone", 29.99, ProdutoTipo.ELETRÔNICO, 10, "Stax", "Headphone preto");
        dao.cadastrar(produto);
        
        List<Produto> produtosCadastrados = dao.buscar("Headphone");
        assertNotNull(produtosCadastrados);
        assertTrue(!produtosCadastrados.isEmpty());
        
        for (Produto cadastrado : produtosCadastrados) {
            if(cadastrado.equals(produto)) {
        	assertEquals("Headphone", cadastrado.getNome());
        	assertEquals("Headphone preto", cadastrado.getDescricao());
            assertEquals("Stax", cadastrado.getMarca());
            assertEquals((Integer)10, cadastrado.getQuantidade());
            assertEquals(ProdutoTipo.ELETRÔNICO, cadastrado.getTipo());
            assertEquals(29.99, cadastrado.getValor(), 0.01);
            }
        }
        dao.remover(produto);
    }
    
    @Test
    public void testBuscar() {
    	List<Produto> produtos = dao.buscar("Camiseta");
    	assertNotNull(produtos);
        assertTrue(produtos.isEmpty());
        
        Produto novoProduto = new Produto("Headphone", 79.99, ProdutoTipo.ELETRÔNICO, 5, "Stax", "Headphone preto");
        dao.cadastrar(novoProduto);
        
        List<Produto> produtosEncontrados = dao.buscar("Headphone");
        assertNotNull(produtosEncontrados);
        assertFalse(produtosEncontrados.isEmpty());
        
        
        for (Produto encontrado : produtosEncontrados) {
            if (encontrado.equals(novoProduto)) {
                assertEquals("Headphone", encontrado.getNome());
                assertEquals("Headphone preto", encontrado.getDescricao());
                assertEquals("Stax", encontrado.getMarca());
                assertEquals((Integer) 5, encontrado.getQuantidade());
                assertEquals(ProdutoTipo.ELETRÔNICO, encontrado.getTipo());
                assertEquals(79.99, encontrado.getValor(), 0.01);
            }
        }
        dao.remover(novoProduto);
    }
    
    @Test
    public void testAtualizar() {
    	Produto produto = new Produto("Headphone", 29.99, ProdutoTipo.ELETRÔNICO, 10, "Stax", "Headphone preto");
        dao.cadastrar(produto);
        
        Produto atualizado = new Produto("Headphone", 39.99, ProdutoTipo.ELETRÔNICO, 10, "Stax", "Headphone preto");
        dao.atualizar(atualizado);
        
        List<Produto> modificado = dao.buscar("Headphone");
        assertNotNull(modificado);
        assertFalse(modificado.isEmpty());
        
        
        for (Produto produtoModificado : modificado) {
            if(produtoModificado.equals(atualizado)){
            assertEquals("Headphone", produtoModificado.getNome());
            assertEquals("Headphone preto", produtoModificado.getDescricao());
            assertEquals("Stax", produtoModificado.getMarca());
            assertEquals((Integer)10, produtoModificado.getQuantidade());
            assertEquals(ProdutoTipo.ELETRÔNICO, produtoModificado.getTipo());
            assertEquals(39.99, produtoModificado.getValor(), 0.01);
            }
        }
        dao.remover(atualizado);
    }
    
    @Test
    public void testRemover() {
        Produto produto = new Produto("Headphone", 29.99, ProdutoTipo.ELETRÔNICO, 10, "Stax", "Headphone preto");
        dao.cadastrar(produto);
        
        dao.remover(produto);
        
        List<Produto> naoEncontrado = dao.buscar("Headphone");
        assertNotNull(naoEncontrado);
        assertTrue(naoEncontrado.isEmpty());
    }
    
}
