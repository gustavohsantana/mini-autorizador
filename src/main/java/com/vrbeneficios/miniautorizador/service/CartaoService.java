package com.vrbeneficios.miniautorizador.service;

import com.vrbeneficios.miniautorizador.model.Cartao;
import com.vrbeneficios.miniautorizador.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public Cartao criarCartao(Cartao cartao) {
        return cartaoRepository.save(cartao);
    }

    public Double obterSaldo(String numeroCartao) {
        Cartao cartao = cartaoRepository.findById(numeroCartao).orElse(null);
        if (cartao != null) {
            return cartao.getSaldo();
        }
        return null;
    }
}
