package com.vrbeneficios.miniautorizador.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoRequest {

    private String numeroCartao;

    private String senhaCartao;

    private String valor;
}
