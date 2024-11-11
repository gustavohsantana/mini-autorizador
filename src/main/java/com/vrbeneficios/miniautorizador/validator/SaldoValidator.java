package com.vrbeneficios.miniautorizador.validator;

import com.vrbeneficios.miniautorizador.dto.TransacaoRequest;
import com.vrbeneficios.miniautorizador.exceptions.SaldoInsuficienteException;
import com.vrbeneficios.miniautorizador.model.Cartao;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SaldoValidator implements Validator {
    @Override
    public void validate(TransacaoRequest request, Cartao cartao) {
        BigDecimal valorTransacao = new BigDecimal(request.getValor());
        if (cartao.getSaldo().compareTo(valorTransacao) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }
    }

}
