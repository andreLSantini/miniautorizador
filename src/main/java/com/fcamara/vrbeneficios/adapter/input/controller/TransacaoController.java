package com.fcamara.vrbeneficios.adapter.input.controller;

import com.fcamara.vrbeneficios.adapter.output.request.CriarCartaoRequest;
import com.fcamara.vrbeneficios.adapter.output.request.TransacaoRequest;
import com.fcamara.vrbeneficios.domain.model.CartaoEntity;
import com.fcamara.vrbeneficios.domain.model.TransacaoEntity;
import com.fcamara.vrbeneficios.port.input.AutorizarTransacaoUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private AutorizarTransacaoUseCase autorizarTransacao;

    @PostMapping
    public ResponseEntity<String> autorizarTransacao(@RequestBody TransacaoRequest transacaoRequest) {
        autorizarTransacao.execute(createTrasasacaoEntity(transacaoRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }

    private TransacaoEntity createTrasasacaoEntity(TransacaoRequest transacaoRequest) {
        return new TransacaoEntity(transacaoRequest.getNumeroCartao(), transacaoRequest.getSenha(), transacaoRequest.getValor());
    }
}
