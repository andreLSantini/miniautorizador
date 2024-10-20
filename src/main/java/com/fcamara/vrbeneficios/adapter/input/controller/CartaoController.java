package com.fcamara.vrbeneficios.adapter.input.controller;


import com.fcamara.vrbeneficios.adapter.output.request.CriarCartaoRequest;
import com.fcamara.vrbeneficios.adapter.output.response.CartaoCriadoResponse;
import com.fcamara.vrbeneficios.domain.model.CartaoEntity;
import com.fcamara.vrbeneficios.port.input.CriarCartaoUseCase;
import com.fcamara.vrbeneficios.port.input.ObterSaldoCartaoUseCase;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
@Slf4j
public class CartaoController {

    @Autowired
    private CriarCartaoUseCase criarCartaoUseCase;
    @Autowired
    private ObterSaldoCartaoUseCase obterSaldoCartaoUseCase;

    @PostMapping
    public ResponseEntity<CartaoCriadoResponse> criarCartao(@Valid @RequestBody CriarCartaoRequest criarCartaoRequest) {
        log.info("Requisição para criação de cartão recebida. Número do Cartão: {}", criarCartaoRequest.getNumeroCartao());
        return ResponseEntity.ok(criarCartaoUseCase.execute(createCartaoEntity(criarCartaoRequest)));
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> obterSaldo(@PathVariable String numeroCartao) {
        log.info("Requisição para obter saldo do cartão. Número do Cartão: {}", numeroCartao);
        return ResponseEntity.ok(obterSaldoCartaoUseCase.execute(numeroCartao));
    }

    private CartaoEntity createCartaoEntity(CriarCartaoRequest criarCartaoRequest) {
        log.debug("Criando CartaoEntity a partir do request: {}", criarCartaoRequest);
        return new CartaoEntity(criarCartaoRequest.getNumeroCartao(), criarCartaoRequest.getSenha());
    }

}
