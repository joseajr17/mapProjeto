package Testes;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.dao.impl.CompradorDaoImpl;
import model.entities.Comprador;

public class CompradorDaoImplTest {

    private CompradorDaoImpl dao;
    
    @Before
    public void setUp() {
        dao = new CompradorDaoImpl();
    }
    
    @Test
    public void testCadastrar() {
        Comprador comprador = new Comprador("João", "joao@gmail.com", "123456", "12345678901", "Rua A");
        dao.cadastrar(comprador);
        
        Comprador cadastrado = dao.buscar("joao@gmail.com");
        assertNotNull(cadastrado);
        assertEquals("João", cadastrado.getNome());
        assertEquals("joao@gmail.com", cadastrado.getEmail());
        assertEquals("123456", cadastrado.getSenha());
        assertEquals("12345678901", cadastrado.getCpf());
        assertEquals("Rua A", cadastrado.getEndereco());
        dao.remover("12345678901");
    }
    
    @Test
    public void testBuscar() {
        Comprador comprador = dao.buscar("joao@gmail.com");
        assertNull(comprador);
        
        Comprador novoComprador = new Comprador("Maria", "maria@gmail.com", "654321", "98765432109", "Rua B");
        dao.cadastrar(novoComprador);
        
        Comprador encontrado = dao.buscar("maria@gmail.com");
        assertNotNull(encontrado);
        assertEquals("Maria", encontrado.getNome());
        assertEquals("maria@gmail.com", encontrado.getEmail());
        assertEquals("654321", encontrado.getSenha());
        assertEquals("98765432109", encontrado.getCpf());
        assertEquals("Rua B", encontrado.getEndereco());
        dao.remover("98765432109");
    }
    
    @Test
    public void testAtualizar() {
        Comprador comprador = new Comprador("José", "jose@gmail.com", "987654", "11122233344", "Rua C");
        dao.cadastrar(comprador);
        
        Comprador atualizado = new Comprador("José da Silva", "jose@gmail.com", "123456", "11122233344", "Rua D");
        dao.atualizar(atualizado);
        
        Comprador modificado = dao.buscar("jose@gmail.com");
        assertNotNull(modificado);
        assertEquals("José da Silva", modificado.getNome());
        assertEquals("jose@gmail.com", modificado.getEmail());
        assertEquals("123456", modificado.getSenha());
        assertEquals("11122233344", modificado.getCpf());
        assertEquals("Rua D", modificado.getEndereco());
        dao.remover("11122233344");
    }
    
    @Test
    public void testRemover() {
        Comprador comprador = new Comprador("Ana", "ana@gmail.com", "654321", "55544433322", "Rua E");
        dao.cadastrar(comprador);
        
         dao.remover("55544433322");
        
        Comprador naoEncontrado = dao.buscar("ana@gmail.com");
        assertNull(naoEncontrado);
    }
}