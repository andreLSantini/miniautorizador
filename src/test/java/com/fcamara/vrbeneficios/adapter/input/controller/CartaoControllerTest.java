package com.fcamara.vrbeneficios.adapter.input.controller;

import com.fcamara.vrbeneficios.adapter.output.request.CriarCartaoRequest;
import com.fcamara.vrbeneficios.port.input.CriarCartaoUseCase;
import com.fcamara.vrbeneficios.port.input.ObterSaldoCartaoUseCase;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartaoControllerTest {

    @Mock
    private CriarCartaoUseCase criarCartaoUseCase;

    @Mock
    private ObterSaldoCartaoUseCase obterSaldoCartaoUseCase;

    @InjectMocks
    private CartaoController cartaoController;

    private CriarCartaoRequest criarCartaoRequest;

    @BeforeEach
    public void setUp() {
        criarCartaoRequest = new CriarCartaoRequest();
        criarCartaoRequest.setNumeroCartao("6549873025634501");
        criarCartaoRequest.setSenha("1234");
    }

    @Test
    @DisplayName("Deve Criar um cartao com sucesso")
    void shouldCreateCardSuccess() {
        var response = cartaoController.criarCartao(criarCartaoRequest);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }


    @Test
    @DisplayName("Deve Obter saldo do cartao existente")
    void shouldGetBalanceSuccess() {
        String numeroCartao = "6549873025634501";
        var saldo = new BigDecimal("400.00");
        when(obterSaldoCartaoUseCase.execute(numeroCartao)).thenReturn(saldo);
        var response = cartaoController.obterSaldo(numeroCartao);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), saldo);
    }


}