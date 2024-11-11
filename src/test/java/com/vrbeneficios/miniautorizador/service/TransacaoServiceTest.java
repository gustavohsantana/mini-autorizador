package com.vrbeneficios.miniautorizador.service;

import com.vrbeneficios.miniautorizador.exceptions.CartaoNaoEncontradoException;
import com.vrbeneficios.miniautorizador.exceptions.SaldoInsuficienteException;
import com.vrbeneficios.miniautorizador.exceptions.SenhaInvalidaException;
import com.vrbeneficios.miniautorizador.model.Cartao;
import com.vrbeneficios.miniautorizador.repository.CartaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransacaoServiceTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private TransacaoService transacaoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void lancarExcecaoQuandoCartaoNaoForEncontrado() {
        // Configuração
        String numeroCartao = "1234567890123456";
        String senhaCartao = "1234";
        String valor = "50.00";

        when(cartaoRepository.findById(numeroCartao)).thenReturn(Optional.empty());

        // Execução e verificação
        assertThrows(CartaoNaoEncontradoException.class, () ->
                transacaoService.realizarTransacao(numeroCartao, senhaCartao, valor)
        );

        verify(cartaoRepository, times(1)).findById(numeroCartao);
    }

    @Test
    public void lancarExcecaoQuandoSenhaForInvalida() {
        // Configuração
        String numeroCartao = "1234567890123456";
        String senhaCartao = "1234";
        String valor = "50.00";

        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(numeroCartao);
        cartao.setSenha("0000"); // senha incorreta
        cartao.setSaldo(BigDecimal.valueOf(100.00));

        when(cartaoRepository.findById(numeroCartao)).thenReturn(Optional.of(cartao));

        // Execução e verificação
        assertThrows(SenhaInvalidaException.class, () ->
                transacaoService.realizarTransacao(numeroCartao, senhaCartao, valor)
        );

        verify(cartaoRepository, times(1)).findById(numeroCartao);
    }

    @Test
    public void lancarExcecaoQuandoSaldoForInsuficiente() {
        // Configuração
        String numeroCartao = "1234567890123456";
        String senhaCartao = "1234";
        String valor = "150.00"; // valor maior que o saldo

        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(numeroCartao);
        cartao.setSenha(senhaCartao);
        cartao.setSaldo(BigDecimal.valueOf(100.00));

        when(cartaoRepository.findById(numeroCartao)).thenReturn(Optional.of(cartao));

        // Execução e verificação
        assertThrows(SaldoInsuficienteException.class, () ->
                transacaoService.realizarTransacao(numeroCartao, senhaCartao, valor)
        );

        verify(cartaoRepository, times(1)).findById(numeroCartao);
    }

    @Test
    public void realizarTransacaoComSucesso() {
        // Configuração
        String numeroCartao = "1234567890123456";
        String senhaCartao = "1234";
        String valor = "50.00"; // valor dentro do saldo disponível

        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(numeroCartao);
        cartao.setSenha(senhaCartao);
        cartao.setSaldo(BigDecimal.valueOf(100.00));

        when(cartaoRepository.findById(numeroCartao)).thenReturn(Optional.of(cartao));

        // Execução
        transacaoService.realizarTransacao(numeroCartao, senhaCartao, valor);

        // Verificação
        assertEquals(50.00, cartao.getSaldo()); // saldo após transação
        verify(cartaoRepository, times(1)).save(cartao);
    }
}
