package com.vrbeneficios.miniautorizador.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@Builder
public class Cartao {

    @Id
    private String numeroCartao;
    private String senha;
    private BigDecimal saldo;

    public Cartao() {
        this.saldo = BigDecimal.valueOf(500.00); // Saldo inicial de RS 500,00
    }

}
