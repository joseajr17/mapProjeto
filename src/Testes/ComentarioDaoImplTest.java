package Testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.dao.ComentarioDao;
import model.dao.impl.ComentarioDaoImpl;
import model.entities.Comentario;
import model.entities.Loja;
import model.entities.Produto;
import model.entities.ProdutoTipo;

public class ComentarioDaoImplTest {
    private ComentarioDaoImpl comentarioDao;
    @Before
    public void setUp() {
        comentarioDao = new ComentarioDaoImpl();
    }

    @Test
    public void adiconarTest(){
        Loja loja = new Loja("lojateste1", "testelojaEmail", "testesenha", "cpfteste", "teste", null);
        assertTrue(comentarioDao.listar(loja).isEmpty());
         Produto produto = new Produto("Headphone", 79.99, ProdutoTipo.ELETRONICO, 5, "Stax", "Headphone preto", "testelojaEmail");
        
        Comentario comentario = new Comentario("teste1", produto);
        comentarioDao.adicionar(comentario);
        assertFalse(comentarioDao.listar(loja).isEmpty());
        comentarioDao.remover(comentario);
    }

    @Test
    public void listarTest(){
        Loja loja = new Loja("lojateste1", "testelojaEmail", "testesenha", "cpfteste", "teste", null);
        assertTrue(comentarioDao.listar(loja).isEmpty());
         Produto produto = new Produto("Headphone", 79.99, ProdutoTipo.ELETRONICO, 5, "Stax", "Headphone preto", "testelojaEmail");
        
        Comentario comentario = new Comentario("teste1", produto);
        comentarioDao.adicionar(comentario);
        assertFalse(comentarioDao.listar(loja).isEmpty());
         Produto produto2 = new Produto("teste2", 79.99, ProdutoTipo.ELETRONICO, 5, "test", "teste 2", "testelojaEmail");
        
        Comentario comentario2 = new Comentario("teste2", produto2);
        comentarioDao.adicionar(comentario2);

        assertTrue(comentarioDao.listar(loja).contains(comentario));
        assertTrue(comentarioDao.listar(loja).contains(comentario2));

        comentarioDao.remover(comentario);
        comentarioDao.remover(comentario2);
    }

    @Test
    public void removerTest(){
        Loja loja = new Loja("lojateste1", "testelojaEmail", "testesenha", "cpfteste", "teste", null);
        assertTrue(comentarioDao.listar(loja).isEmpty());
         Produto produto = new Produto("Headphone", 79.99, ProdutoTipo.ELETRONICO, 5, "Stax", "Headphone preto", "testelojaEmail");
        
        Comentario comentario = new Comentario("teste1", produto);
        assertEquals("Comentário não encontrado", comentarioDao.remover(comentario));
        comentarioDao.adicionar(comentario);
        assertFalse(comentarioDao.listar(loja).isEmpty());
        assertEquals("Comentário removido com sucesso!",comentarioDao.remover(comentario));
    }
}
