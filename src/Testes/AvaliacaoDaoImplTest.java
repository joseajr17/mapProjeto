package Testes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.dao.impl.AvaliacaoDaoImpl;
import model.entities.Avaliacao;
import model.entities.Loja;
import model.entities.Produto;
import model.entities.ProdutoTipo;

public class AvaliacaoDaoImplTest {
    private AvaliacaoDaoImpl avaliacaoDao;
    @Before
    public void setUp() {
    	avaliacaoDao = new AvaliacaoDaoImpl();
    }

    @Test
    public void adiconarTest(){
        Loja loja = new Loja("lojateste1", "testelojaEmail", "testesenha", "cpfteste", "teste", null);
        assertTrue(avaliacaoDao.listar(loja).isEmpty());
         Produto produto = new Produto("Headphone", 79.99, ProdutoTipo.ELETRONICO, 5, "Stax", "Headphone preto", "testelojaEmail");
        
        Avaliacao comentario = new Avaliacao("teste1", produto);
        avaliacaoDao.adicionar(comentario);
        assertFalse(avaliacaoDao.listar(loja).isEmpty());
        avaliacaoDao.remover(loja);
    }

    @Test
    public void listarTest(){
        Loja loja = new Loja("lojateste1", "testelojaEmail", "testesenha", "cpfteste", "teste", null);
        assertTrue(avaliacaoDao.listar(loja).isEmpty());
         Produto produto = new Produto("Headphone", 79.99, ProdutoTipo.ELETRONICO, 5, "Stax", "Headphone preto", "testelojaEmail");
        
        Avaliacao comentario = new Avaliacao("teste1", produto);
        avaliacaoDao.adicionar(comentario);
        assertFalse(avaliacaoDao.listar(loja).isEmpty());
         Produto produto2 = new Produto("teste2", 79.99, ProdutoTipo.ELETRONICO, 5, "test", "teste 2", "testelojaEmail");
        
        Avaliacao comentario2 = new Avaliacao("teste2", produto2);
        avaliacaoDao.adicionar(comentario2);

        assertTrue(avaliacaoDao.listar(loja).contains(comentario));
        assertTrue(avaliacaoDao.listar(loja).contains(comentario2));

        avaliacaoDao.remover(loja);
       // comentarioDao.remover(comentario2);
    }

    @Test
    public void removerTest(){
        Loja loja = new Loja("lojateste1", "testelojaEmail", "testesenha", "cpfteste", "teste", null);
        assertTrue(avaliacaoDao.listar(loja).isEmpty());
         Produto produto = new Produto("Headphone", 79.99, ProdutoTipo.ELETRONICO, 5, "Stax", "Headphone preto", "testelojaEmail");
        
        Avaliacao comentario = new Avaliacao("teste1", produto);
        //assertEquals("Comentário não encontrado", comentarioDao.remover(comentario));
        avaliacaoDao.adicionar(comentario);
        assertFalse(avaliacaoDao.listar(loja).isEmpty());
        avaliacaoDao.remover(loja);
       assertTrue(avaliacaoDao.listar(loja).isEmpty());
    }

}
