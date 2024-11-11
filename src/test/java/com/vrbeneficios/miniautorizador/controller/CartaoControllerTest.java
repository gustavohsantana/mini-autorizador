package com.vrbeneficios.miniautorizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vrbeneficios.miniautorizador.dto.TransacaoRequest;
import com.vrbeneficios.miniautorizador.exceptions.CartaoNaoEncontradoException;
import com.vrbeneficios.miniautorizador.exceptions.SaldoInsuficienteException;
import com.vrbeneficios.miniautorizador.model.Cartao;
import com.vrbeneficios.miniautorizador.service.CartaoService;
import com.vrbeneficios.miniautorizador.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CartaoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CartaoService cartaoService;

    @Mock
    private TransacaoService transacaoService;

    @InjectMocks
    private CartaoController cartaoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartaoController).build();
    }

    @Test
    public void testCriarCartao_Sucesso() throws Exception {
        Cartao cartao = new Cartao("1234567890123456", "1234", new BigDecimal(1000));

        when(cartaoService.obterSaldo(cartao.getNumeroCartao())).thenReturn(null);
        when(cartaoService.criarCartao(any(Cartao.class))).thenReturn(cartao);

        mockMvc.perform(post("/cartoes/criar")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(cartao)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroCartao").value("1234567890123456"))
                .andExpect(jsonPath("$.saldo").value(1000));
    }

    @Test
    public void testCriarCartao_CartaoJaExiste() throws Exception {
        Cartao cartao = new Cartao("1234567890123456", "1234", new BigDecimal(1000));

        when(cartaoService.obterSaldo(cartao.getNumeroCartao())).thenReturn(BigDecimal.TEN);

        mockMvc.perform(post("/cartoes/criar")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(cartao)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testObterSaldoCartao_Sucesso() throws Exception {
        String numeroCartao = "1234567890123456";
        BigDecimal saldo = new BigDecimal(500);

        when(cartaoService.obterSaldo(numeroCartao)).thenReturn(saldo);

        mockMvc.perform(get("/cartoes/{numeroCartao}/saldo", numeroCartao))
                .andExpect(status().isOk())
                .andExpect(content().string("500"));
    }

    @Test
    public void testObterSaldoCartao_CartaoNaoEncontrado() throws Exception {
        String numeroCartao = "1234567890123456";

        when(cartaoService.obterSaldo(numeroCartao)).thenReturn(null);

        mockMvc.perform(get("/cartoes/{numeroCartao}/saldo", numeroCartao))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRealizarTransacao_Sucesso() throws Exception {
        TransacaoRequest transacaoRequest = new TransacaoRequest("1234567890123456", "1234", "100");

        mockMvc.perform(post("/cartoes/transacao")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(transacaoRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Transação realizada com sucesso!"));
    }

    @Test
    public void testRealizarTransacao_CartaoNaoEncontrado() throws Exception {
        TransacaoRequest transacaoRequest = new TransacaoRequest("1234567890123456", "1234", "100");

        doThrow(new CartaoNaoEncontradoException("Cartão não encontrado")).when(transacaoService).realizarTransacao(any(TransacaoRequest.class));

        mockMvc.perform(post("/cartoes/transacao")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(transacaoRequest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cartão não encontrado"));
    }

    @Test
    public void testRealizarTransacao_SaldoInsuficiente() throws Exception {
        TransacaoRequest transacaoRequest = new TransacaoRequest("1234567890123456", "1234", "100");

        doThrow(new SaldoInsuficienteException("Saldo insuficiente")).when(transacaoService).realizarTransacao(any(TransacaoRequest.class));

        mockMvc.perform(post("/cartoes/transacao")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(transacaoRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Saldo insuficiente"));
    }

    @Test
    public void testListarTodosCartoes() throws Exception {
        Cartao cartao = new Cartao("1234567890123456", "1234", new BigDecimal(1000));

        when(cartaoService.getAllCartoes()).thenReturn(Collections.singletonList(cartao));

        mockMvc.perform(get("/cartoes/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroCartao").value("1234567890123456"))
                .andExpect(jsonPath("$[0].saldo").value(1000));
    }
}