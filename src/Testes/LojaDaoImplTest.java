package Testes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.After;

import model.dao.impl.LojaDaoImpl;
import model.entities.Loja;

public class LojaDaoImplTest {
    private LojaDaoImpl lojaDao;

    @Before
    public void setUp() {
        lojaDao = new LojaDaoImpl();
    }

    @After
    public void at() {
        lojaDao.remover("123.456.789-09");
    }

    @Test
    public void testCadastrar() {
        Loja loja = new Loja();
        loja.setNome("Minha Loja");
        loja.setEmail("loja@gmail.com");
        loja.setCpfOUcnpj("123.456.789-09");
        loja.setEndereco("Rua da Loja, 123");
        loja.setSenha("123456");

        lojaDao.cadastrar(loja);

        Loja lojaCadastrada = lojaDao.buscarPeloNome("Minha Loja");

        assertNotNull(lojaCadastrada);
        assertEquals("Minha Loja", lojaCadastrada.getNome());
        assertEquals("loja@gmail.com", lojaCadastrada.getEmail());
        assertEquals("123.456.789-09", lojaCadastrada.getCpfOUcnpj());
        assertEquals("Rua da Loja, 123", lojaCadastrada.getEndereco());
        assertEquals("123456", lojaCadastrada.getSenha());
    }

    @Test
    public void testBuscarPeloNome() {
        Loja loja = new Loja();
        loja.setNome("Minha Loja");
        loja.setEmail("loja@gmail.com");
        loja.setCpfOUcnpj("123.456.789-09");
        loja.setEndereco("Rua da Loja, 123");
        loja.setSenha("123456");

        lojaDao.cadastrar(loja);
        Loja lojaEncontrada = lojaDao.buscarPeloNome("Minha Loja");

        assertNotNull(lojaEncontrada);
        assertEquals("Minha Loja", lojaEncontrada.getNome());
    }

    @Test
    public void testBuscarPeloEmail() {
        Loja loja = new Loja();
        loja.setNome("Minha Loja");
        loja.setEmail("loja@gmail.com");
        loja.setCpfOUcnpj("123.456.789-09");
        loja.setEndereco("Rua da Loja, 123");
        loja.setSenha("123456");

        lojaDao.cadastrar(loja);
        Loja lojaEncontrada = lojaDao.buscarPeloEmail("loja@gmail.com");

        assertNotNull(lojaEncontrada);
        assertEquals("loja@gmail.com", lojaEncontrada.getEmail());
    }

    @Test
    public void testAtualizar() {
        Loja loja = new Loja();
        loja.setNome("Minha Loja");
        loja.setEmail("loja@gmail.com");
        loja.setCpfOUcnpj("123.456.789-09");
        loja.setEndereco("Rua da Loja, 123");
        loja.setSenha("123456");

        lojaDao.cadastrar(loja);
        Loja loja2 = new Loja();
        loja2.setCpfOUcnpj("123.456.789-09");
        loja2.setNome("Nova Loja");
        loja2.setEmail("nova@gmail.com");
        loja2.setEndereco("rua");

        lojaDao.atualizar(loja2);

        Loja lojaAtualizada = lojaDao.buscarPeloNome("Nova Loja");

        assertNotNull(lojaAtualizada);
        assertEquals("Nova Loja", lojaAtualizada.getNome());
        assertEquals("nova@gmail.com", lojaAtualizada.getEmail());
    }

    @Test
    public void testRemover() {
        Loja loja = new Loja();
        loja.setNome("Minha Loja");
        loja.setEmail("loja@gmail.com");
        loja.setCpfOUcnpj("123.456.789-09");
        loja.setEndereco("Rua da Loja, 123");
        loja.setSenha("123456");

        lojaDao.cadastrar(loja);
        lojaDao.remover("123.456.789-09");

        Loja lojaRemovida = lojaDao.buscarPeloNome("Nova Loja");

        assertNull(lojaRemovida);
    }
}