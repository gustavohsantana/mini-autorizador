package com.vrbeneficios.miniautorizador.service;

import com.vrbeneficios.miniautorizador.model.Cartao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CartaoServiceTest {

    @Autowired
    private CartaoService cartaoService;

    @Test
    public void testCriarCartao() {
        Cartao cartao = new Cartao("123456789012", "senha123", 1000.0);
        Cartao novoCartao = cartaoService.criarCartao(cartao);
        assertNotNull(novoCartao);
        assertEquals(cartao.getNumeroCartao(), novoCartao.getNumeroCartao());
    }

    @Test
    public void testObterSaldoCartao() {
        Cartao cartao = new Cartao("123456789012", "senha123", 1000.0);
        Double saldoCartao = cartaoService.obterSaldo(cartao.getNumeroCartao());
        assertNotNull(saldoCartao);
        assertEquals(cartao.getSaldo(), saldoCartao);
    }

}
