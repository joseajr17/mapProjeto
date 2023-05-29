package Testes;

import org.junit.Assert;
import org.junit.Test;

import util.Validar;

public class ValidarTeste {
    @Test
    public void testValidarCpf() {
        // CPF válido
        Assert.assertTrue(Validar.validarCpf("123.456.789-09"));

        // CPF inválido
        Assert.assertFalse(Validar.validarCpf("111.111.111-11"));
        Assert.assertFalse(Validar.validarCpf("123.456.789-10"));
        Assert.assertFalse(Validar.validarCpf("123.abc.789-09"));
    }

    @Test
    public void testValidarCpfOuCnpj() {
        // CPF válido
        Assert.assertTrue(Validar.validarCpfOuCnpj("123.456.789-09"));

        // CNPJ válido
        Assert.assertTrue(Validar.validarCpfOuCnpj("60.746.948/0001-12"));

        // CPF inválido
        Assert.assertFalse(Validar.validarCpfOuCnpj("111.111.111-11"));
        Assert.assertFalse(Validar.validarCpfOuCnpj("123.456.789-10"));
        Assert.assertFalse(Validar.validarCpfOuCnpj("123.abc.789-09"));

        // CNPJ inválido
        Assert.assertFalse(Validar.validarCpfOuCnpj("00.000.000/0000-00"));
        Assert.assertFalse(Validar.validarCpfOuCnpj("12.345.678/0001-10"));
        Assert.assertFalse(Validar.validarCpfOuCnpj("12.345.678/0001a09"));
    }

    @Test
    public void testValidarEmail() {
        // E-mail válido
        Assert.assertTrue(Validar.validarEmail("adriana@gmail.com"));

        // E-mail inválido
        Assert.assertFalse(Validar.validarEmail("adrianasilva.com"));
        Assert.assertFalse(Validar.validarEmail("adriana@"));
        Assert.assertFalse(Validar.validarEmail("@gmail.com"));
    }
}
