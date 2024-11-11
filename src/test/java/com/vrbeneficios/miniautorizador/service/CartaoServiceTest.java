package com.vrbeneficios.miniautorizador.service;

import com.vrbeneficios.miniautorizador.model.Cartao;
import com.vrbeneficios.miniautorizador.repository.CartaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CartaoServiceTest {

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private CartaoRepository cartaoRepository;

    @BeforeEach
    public void setUp() {
        Cartao cartao = new Cartao("111111111", "senha123", BigDecimal.valueOf(1000.00));
        cartaoRepository.save(cartao);
    }

    @Test
    public void criarCartao() {
        Cartao cartao = new Cartao("222222222", "senha123", BigDecimal.valueOf(1000.00));
        Cartao novoCartao = cartaoService.criarCartao(cartao);
        assertNotNull(novoCartao);
        assertEquals(cartao.getNumeroCartao(), novoCartao.getNumeroCartao());
    }

    @Test
    public void obterSaldoCartao() {
        BigDecimal saldoCartao = cartaoService.obterSaldo("111111111");
        assertNotNull(saldoCartao);
        assertEquals(new BigDecimal("1000.00").setScale(2), saldoCartao.setScale(2));
    }
}