package com.vrbeneficios.miniautorizador.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransacaoRequestTest {

    @Test
    public void testTransacaoRequestConstructor() {
        TransacaoRequest transacaoRequest = new TransacaoRequest("1234567890123456", "1234", "100");

        assertNotNull(transacaoRequest);
        assertEquals("1234567890123456", transacaoRequest.getNumeroCartao());
        assertEquals("1234", transacaoRequest.getSenhaCartao());
        assertEquals("100", transacaoRequest.getValor());
    }

    @Test
    public void testSetNumeroCartao() {
        TransacaoRequest transacaoRequest = new TransacaoRequest();
        transacaoRequest.setNumeroCartao("1234567890123456");

        assertEquals("1234567890123456", transacaoRequest.getNumeroCartao());
    }

    @Test
    public void testSetSenhaCartao() {
        TransacaoRequest transacaoRequest = new TransacaoRequest();
        transacaoRequest.setSenhaCartao("1234");

        assertEquals("1234", transacaoRequest.getSenhaCartao());
    }

    @Test
    public void testSetValor() {
        TransacaoRequest transacaoRequest = new TransacaoRequest();
        transacaoRequest.setValor("100");

        assertEquals("100", transacaoRequest.getValor());
    }

}