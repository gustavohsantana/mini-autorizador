package com.vrbeneficios.miniautorizador.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class SenhaInvalidaException extends RuntimeException {

    public SenhaInvalidaException(String message) {
        super(message);
    }
}