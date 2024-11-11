package com.vrbeneficios.miniautorizador.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.vrbeneficios.miniautorizador.dto.TransacaoRequest;
import com.vrbeneficios.miniautorizador.exceptions.CartaoNaoEncontradoException;
import com.vrbeneficios.miniautorizador.exceptions.SaldoInsuficienteException;
import com.vrbeneficios.miniautorizador.exceptions.SenhaInvalidaException;
import com.vrbeneficios.miniautorizador.model.Cartao;
import com.vrbeneficios.miniautorizador.repository.CartaoRepository;
import com.vrbeneficios.miniautorizador.validator.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class TransacaoServiceTest {

    @Autowired
    private TransacaoService transacaoService; // Serviço será injetado pelo Spring

    @MockBean
    private CartaoRepository cartaoRepository;

    @MockBean
    private List<Validator> validators;

    private TransacaoRequest transacaoRequest;
    private Cartao cartao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cartao = new Cartao();
        cartao.setNumeroCartao("123456789");
        cartao.setSenha("1234");
        cartao.setSaldo(new BigDecimal("1000.00"));

        // Inicializa o mock do CartaoRepository
        when(cartaoRepository.findById(cartao.getNumeroCartao())).thenReturn(java.util.Optional.of(cartao));

        transacaoRequest = new TransacaoRequest();
        transacaoRequest.setNumeroCartao("123456789");
        transacaoRequest.setSenhaCartao("1234");
        transacaoRequest.setValor("500.00");
    }

    @Test
    void deveRealizarTransacaoComSucesso() {
        transacaoService.realizarTransacao(transacaoRequest);

        assertTrue(cartao.getSaldo().compareTo(new BigDecimal("500.00")) == 0);

        verify(cartaoRepository).save(cartao);
    }

    @Test
    void deveLancarExcecaoQuandoCartaoNaoEncontrado() {
        when(cartaoRepository.findById(cartao.getNumeroCartao())).thenReturn(java.util.Optional.empty());

        CartaoNaoEncontradoException exception = assertThrows(CartaoNaoEncontradoException.class, () -> {
            transacaoService.realizarTransacao(transacaoRequest);
        });

        assertEquals("Cartão não encontrado", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoSenhaInvalida() {
        transacaoRequest.setSenhaCartao("incorrect");

        SenhaInvalidaException exception = assertThrows(SenhaInvalidaException.class, () -> {
            transacaoService.realizarTransacao(transacaoRequest);
        });

        assertEquals("Senha inválida", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoSaldoInsuficiente() {
        transacaoRequest.setValor("1500.00");

        SaldoInsuficienteException exception = assertThrows(SaldoInsuficienteException.class, () -> {
            transacaoService.realizarTransacao(transacaoRequest);
        });

        assertEquals("Saldo insuficiente", exception.getMessage());
    }
}
