package com.vrbeneficios.miniautorizador.validator;

import com.vrbeneficios.miniautorizador.dto.TransacaoRequest;
import com.vrbeneficios.miniautorizador.model.Cartao;

public interface Validator {
    void validate(TransacaoRequest request, Cartao cartao);
}
