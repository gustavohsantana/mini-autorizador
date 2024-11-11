package com.vrbeneficios.miniautorizador.validator;

import com.vrbeneficios.miniautorizador.dto.TransacaoRequest;
import com.vrbeneficios.miniautorizador.exceptions.SenhaInvalidaException;
import com.vrbeneficios.miniautorizador.model.Cartao;
import org.springframework.stereotype.Component;

@Component
public class SenhaValidator implements Validator {
    @Override
    public void validate(TransacaoRequest request, Cartao cartao) {
        if (!cartao.getSenha().equals(request.getSenhaCartao())) {
            throw new SenhaInvalidaException("Senha inválida");
        }
    }
}

