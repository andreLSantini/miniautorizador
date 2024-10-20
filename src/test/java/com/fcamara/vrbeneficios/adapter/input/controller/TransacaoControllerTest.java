package com.fcamara.vrbeneficios.adapter.input.controller;

import com.fcamara.vrbeneficios.adapter.output.request.CriarCartaoRequest;
import com.fcamara.vrbeneficios.adapter.output.request.TransacaoRequest;
import com.fcamara.vrbeneficios.port.input.AutorizarTransacaoUseCase;
import com.fcamara.vrbeneficios.port.input.CriarCartaoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TransacaoControllerTest {

    @Mock
    private AutorizarTransacaoUseCase autorizarTransacao;

    @InjectMocks
    private TransacaoController transacaoController;

    private TransacaoRequest transacaoRequest;

    @BeforeEach
    public void setUp() {
        transacaoRequest = new TransacaoRequest();
        transacaoRequest.setNumeroCartao("6549873025634501");
        transacaoRequest.setSenha("1234");
        transacaoRequest.setValor(BigDecimal.valueOf(500.00));
    }


    @Test
    @DisplayName("Deve criar uma transacao com sucesso")
    void shouldCreateTransactionSuccess() {
        var response = transacaoController.autorizarTransacao(transacaoRequest);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody(), "OK");
    }
}
