package com.vrbeneficios.miniautorizador.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Cartao {

    @Id
    private String numeroCartao;
    private String senha;
    private Double saldo;

    public Cartao() {
        this.saldo = 500.00; // Saldo inicial de RS 500,00
    }

}
