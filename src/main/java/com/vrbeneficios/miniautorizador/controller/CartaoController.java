package com.vrbeneficios.miniautorizador.controller;

import com.vrbeneficios.miniautorizador.dto.TransacaoRequest;
import com.vrbeneficios.miniautorizador.exceptions.CartaoNaoEncontradoException;
import com.vrbeneficios.miniautorizador.exceptions.SaldoInsuficienteException;
import com.vrbeneficios.miniautorizador.exceptions.SenhaInvalidaException;
import com.vrbeneficios.miniautorizador.model.Cartao;
import com.vrbeneficios.miniautorizador.service.CartaoService;
import com.vrbeneficios.miniautorizador.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<Cartao> criarCartao(@RequestBody Cartao cartao) {
        if (cartaoService.obterSaldo(cartao.getNumeroCartao()) != null) {
            return ResponseEntity.badRequest().body(null);
        }
        Cartao novoCartao = cartaoService.criarCartao(cartao);
        return new ResponseEntity<>(novoCartao, HttpStatus.CREATED);
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> obterCartao(@PathVariable String numeroCartao) {
        BigDecimal saldo = cartaoService.obterSaldo(numeroCartao);
        if (saldo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(saldo, HttpStatus.OK);

    }

    @PostMapping("/transacao")
    public ResponseEntity<String> realizarTransacao(@RequestBody TransacaoRequest transacaoRequest) {
        try {
            // Chama o serviço para realizar a transação
            transacaoService.realizarTransacao(transacaoRequest);
            // Retorna uma resposta de sucesso com status HTTP 200 OK
            return ResponseEntity.ok("Transação realizada com sucesso!");
        } catch (CartaoNaoEncontradoException e) {
            // Em caso de cartão não encontrado, retorna 404 NOT FOUND
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão não encontrado");
        } catch (SenhaInvalidaException e) {
            // Em caso de senha inválida, retorna 400 BAD REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha inválida");
        } catch (SaldoInsuficienteException e) {
            // Em caso de saldo insuficiente, retorna 400 BAD REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo insuficiente");
        } catch (Exception e) {
            // Em caso de erro inesperado, retorna 500 INTERNAL SERVER ERROR
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }

    }

}
