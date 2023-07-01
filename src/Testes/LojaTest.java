package Testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.dao.AvaliacaoDao;
import model.dao.impl.AvaliacaoDaoImpl;
import model.entities.Avaliacao;
import model.entities.Conceito;
import model.entities.Loja;
import model.entities.Produto;
import model.entities.ProdutoTipo;

public class LojaTest {
    
    @Test
    public void testGetConceito(){
        AvaliacaoDao avaliacaoDao = new AvaliacaoDaoImpl();

       Loja loja = new Loja("lojateste1", "testelojaEmail", "testesenha", "cpfteste", "teste", null);
        assertTrue(avaliacaoDao.listar(loja).isEmpty());
         Produto produto = new Produto("Headphone", 79.99, ProdutoTipo.ELETRONICO, 5, "Stax", "Headphone preto", "testelojaEmail");
        
        Avaliacao comentario = new Avaliacao("teste1", produto, 5);
        avaliacaoDao.adicionar(comentario);
        assertEquals(Conceito.EXCELENTE, loja.getConceito());

        Produto produto2 = new Produto("teste3", 79.99, ProdutoTipo.ELETRONICO, 5, "Stax", "Headphone preto", "testelojaEmail");
        
        Avaliacao comentario2 = new Avaliacao("teste1", produto2, 3);
        avaliacaoDao.adicionar(comentario2);
        assertEquals(Conceito.BOM, loja.getConceito());
        avaliacaoDao.remover(loja);
    }
}
