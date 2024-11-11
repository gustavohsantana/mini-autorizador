package com.vrbeneficios.miniautorizador.service;

import com.vrbeneficios.miniautorizador.dto.TransacaoRequest;
import com.vrbeneficios.miniautorizador.exceptions.CartaoNaoEncontradoException;
import com.vrbeneficios.miniautorizador.model.Cartao;
import com.vrbeneficios.miniautorizador.repository.CartaoRepository;
import com.vrbeneficios.miniautorizador.validator.Validator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private List<Validator> validators;

    @Transactional
    public void realizarTransacao(TransacaoRequest transacaoRequest) {

        Cartao cartao = cartaoRepository.findById(transacaoRequest.getNumeroCartao())
                .orElseThrow(() -> new CartaoNaoEncontradoException("Cartão não encontrado"));

        // Executa cada validação na cadeia
        validators.stream()
                .forEach(validator -> validator.validate(transacaoRequest, cartao));

        // Realiza a transação
        BigDecimal valorTransacao = new BigDecimal(transacaoRequest.getValor());
        BigDecimal novoSaldo = cartao.getSaldo().subtract(valorTransacao);
        cartao.setSaldo(novoSaldo);

        cartaoRepository.save(cartao);
    }
}
