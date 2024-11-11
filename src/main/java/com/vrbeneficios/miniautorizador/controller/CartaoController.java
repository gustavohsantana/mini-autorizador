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
import java.util.List;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping("/criar")
    public ResponseEntity<Cartao> criarCartao(@RequestBody Cartao cartao) {
        if (cartaoService.obterSaldo(cartao.getNumeroCartao()) != null) {
            return ResponseEntity.badRequest().body(null);
        }
        Cartao novoCartao = cartaoService.criarCartao(cartao);
        return new ResponseEntity<>(novoCartao, HttpStatus.CREATED);
    }

    @GetMapping("/{numeroCartao}/saldo")
    public ResponseEntity<BigDecimal> obterSaldoCartao(@PathVariable String numeroCartao) {
        BigDecimal saldo = cartaoService.obterSaldo(numeroCartao);
        if (saldo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(saldo, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Cartao>> listarTodosCartoes() {
        List<Cartao> cartoes = cartaoService.getAllCartoes();
        return new ResponseEntity<>(cartoes, HttpStatus.OK);
    }

    @PostMapping("/transacao")
    public ResponseEntity<String> realizarTransacao(@RequestBody TransacaoRequest transacaoRequest) {
        try {
            transacaoService.realizarTransacao(transacaoRequest);
            return ResponseEntity.ok("Transação realizada com sucesso!");
        } catch (CartaoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão não encontrado");
        } catch (SenhaInvalidaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha inválida");
        } catch (SaldoInsuficienteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo insuficiente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
        }
    }

}