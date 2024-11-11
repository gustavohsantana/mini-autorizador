package com.vrbeneficios.miniautorizador.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CartaoTest {

    @Test
    public void testCartaoConstructor() {
        Cartao cartao = new Cartao("1234567890123456", "1234", new BigDecimal(500));

        assertNotNull(cartao);
        assertEquals("1234567890123456", cartao.getNumeroCartao());
        assertEquals("1234", cartao.getSenha());
        assertEquals(new BigDecimal(500), cartao.getSaldo());
    }

    @Test
    public void testSetNumeroCartao() {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("1234567890123456");

        assertEquals("1234567890123456", cartao.getNumeroCartao());
    }

    @Test
    public void testSetSenha() {
        Cartao cartao = new Cartao();
        cartao.setSenha("1234");

        assertEquals("1234", cartao.getSenha());
    }

    @Test
    public void testSetSaldo() {
        Cartao cartao = new Cartao();
        cartao.setSaldo(new BigDecimal(500));

        assertEquals(new BigDecimal(500), cartao.getSaldo());
    }

    @Test
    public void testToString() {
        Cartao cartao = new Cartao("1234567890123456", "1234", new BigDecimal(500));
        String expected = "Cartao(numeroCartao=1234567890123456, senha=1234, saldo=500)";

        assertEquals(expected, cartao.toString());
    }
}