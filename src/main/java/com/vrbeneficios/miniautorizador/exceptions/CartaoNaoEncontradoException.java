package com.vrbeneficios.miniautorizador.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CartaoNaoEncontradoException extends RuntimeException {

    public CartaoNaoEncontradoException(String message) {
        super(message);
    }
}
