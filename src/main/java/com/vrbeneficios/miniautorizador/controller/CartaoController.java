package com.vrbeneficios.miniautorizador.controller;

import com.vrbeneficios.miniautorizador.model.Cartao;
import com.vrbeneficios.miniautorizador.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    public ResponseEntity<Cartao> criarCartao(@RequestBody Cartao cartao){
        if(cartaoService.obterSaldo(cartao.getNumeroCartao()) != null){
            return ResponseEntity.badRequest().body(null);
        }
        Cartao novoCartao =  cartaoService.criarCartao(cartao);
        return new ResponseEntity<>(novoCartao, HttpStatus.CREATED);
    }

    public ResponseEntity<Double> obterCartao(@PathVariable String numeroCartao){
        Double saldo = cartaoService.obterSaldo(numeroCartao);
        if (saldo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(saldo, HttpStatus.OK);

    }
}
