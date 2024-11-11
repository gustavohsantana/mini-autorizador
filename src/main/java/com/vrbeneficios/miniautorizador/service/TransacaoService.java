package com.vrbeneficios.miniautorizador.service;

import com.vrbeneficios.miniautorizador.exceptions.CartaoNaoEncontradoException;
import com.vrbeneficios.miniautorizador.exceptions.SaldoInsuficienteException;
import com.vrbeneficios.miniautorizador.exceptions.SenhaInvalidaException;
import com.vrbeneficios.miniautorizador.model.Cartao;
import com.vrbeneficios.miniautorizador.repository.CartaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransacaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Transactional
    public void realizarTransacao(String numeroCartao, String senhaCartao, String valor) {

        Cartao cartao = cartaoRepository.findById(numeroCartao)
                .orElseThrow(() -> new CartaoNaoEncontradoException("Cartão não encontrado"));

        if (!cartao.getSenha().equals(senhaCartao)) {
            throw new SenhaInvalidaException("Senha inválida");
        }

        BigDecimal saldoBigDecimal = cartao.getSaldo();

        BigDecimal valorTransacao = new BigDecimal(valor);

        if (saldoBigDecimal.compareTo(valorTransacao) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }

        BigDecimal novoSaldo = saldoBigDecimal.subtract(valorTransacao);

        cartao.setSaldo(novoSaldo);

        cartaoRepository.save(cartao);
    }
}
