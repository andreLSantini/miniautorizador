package com.fcamara.vrbeneficios.integration;

import com.fcamara.vrbeneficios.adapter.output.request.CriarCartaoRequest;
import com.fcamara.vrbeneficios.adapter.output.request.TransacaoRequest;
import com.fcamara.vrbeneficios.adapter.output.response.CartaoCriadoResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class TransacaoControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String numeroCartao;

    @BeforeEach
    public void setUp() {
        numeroCartao = String.valueOf(new Random().nextInt());
    }

    @Test
    @DisplayName("Deve autorizar transação com sucesso")
    public void shouldAuthorizeTransactionSuccessIntegration() {
        CriarCartaoRequest cartaoRequest = CriarCartaoRequest.builder()
                .numeroCartao(numeroCartao)
                .senha("1234")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CriarCartaoRequest> createRequest = new HttpEntity<>(cartaoRequest, headers);

        ResponseEntity<CartaoCriadoResponse> createResponse = this.testRestTemplate
                .withBasicAuth("usuario", "123456")
                .exchange("/cartoes", HttpMethod.POST, createRequest, CartaoCriadoResponse.class);

        assertEquals(HttpStatus.OK, createResponse.getStatusCode());

        TransacaoRequest transacaoRequest = new TransacaoRequest();
        transacaoRequest.setNumeroCartao(numeroCartao);
        transacaoRequest.setSenha("1234");
        transacaoRequest.setValor(BigDecimal.valueOf(100.00));

        HttpEntity<TransacaoRequest> transactionRequest = new HttpEntity<>(transacaoRequest, headers);

        ResponseEntity<String> response = this.testRestTemplate
                .withBasicAuth("usuario", "123456")
                .exchange("/transacoes", HttpMethod.POST, transactionRequest, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Deve falhar ao autorizar transação com senha inválida")
    public void shouldFailAuthorizeTransactionInvalidPassword() {
        CriarCartaoRequest cartaoRequest = CriarCartaoRequest.builder()
                .numeroCartao(numeroCartao)
                .senha("1234")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CriarCartaoRequest> createRequest = new HttpEntity<>(cartaoRequest, headers);

        ResponseEntity<CartaoCriadoResponse> createResponse = this.testRestTemplate
                .withBasicAuth("usuario", "123456")
                .exchange("/cartoes", HttpMethod.POST, createRequest, CartaoCriadoResponse.class);

        assertEquals(HttpStatus.OK, createResponse.getStatusCode());

        TransacaoRequest transacaoRequest = new TransacaoRequest();
        transacaoRequest.setNumeroCartao(numeroCartao);
        transacaoRequest.setSenha("senhaIncorreta");
        transacaoRequest.setValor(BigDecimal.valueOf(100.00));

        HttpEntity<TransacaoRequest> transactionRequest = new HttpEntity<>(transacaoRequest, headers);

        ResponseEntity<String> response = this.testRestTemplate
                .withBasicAuth("usuario", "123456")
                .exchange("/transacoes", HttpMethod.POST, transactionRequest, String.class);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    @DisplayName("Deve falhar ao autorizar transação com cartão inexistente")
    public void shouldFailAuthorizeTransactionCardNotFound() {
        TransacaoRequest transacaoRequest = new TransacaoRequest();
        transacaoRequest.setNumeroCartao(numeroCartao);
        transacaoRequest.setSenha("1234");
        transacaoRequest.setValor(BigDecimal.valueOf(100.00));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TransacaoRequest> transactionRequest = new HttpEntity<>(transacaoRequest, headers);

        ResponseEntity<String> response = this.testRestTemplate
                .withBasicAuth("usuario", "123456")
                .exchange("/transacoes", HttpMethod.POST, transactionRequest, String.class);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    @DisplayName("Deve falhar ao autorizar transação com saldo insuficiente")
    public void shouldFailAuthorizeTransactionInsufficientBalance() {
        CriarCartaoRequest cartaoRequest = CriarCartaoRequest.builder()
                .numeroCartao(numeroCartao)
                .senha("1234")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CriarCartaoRequest> createRequest = new HttpEntity<>(cartaoRequest, headers);

        ResponseEntity<CartaoCriadoResponse> createResponse = this.testRestTemplate
                .withBasicAuth("usuario", "123456")
                .exchange("/cartoes", HttpMethod.POST, createRequest, CartaoCriadoResponse.class);

        assertEquals(HttpStatus.OK, createResponse.getStatusCode());

        TransacaoRequest transacaoRequest = new TransacaoRequest();
        transacaoRequest.setNumeroCartao("6549873025634501");
        transacaoRequest.setSenha("1234");
        transacaoRequest.setValor(BigDecimal.valueOf(600.00));
        HttpEntity<TransacaoRequest> transactionRequest = new HttpEntity<>(transacaoRequest, headers);

        ResponseEntity<String> response = this.testRestTemplate
                .withBasicAuth("usuario", "123456")
                .exchange("/transacoes", HttpMethod.POST, transactionRequest, String.class);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }


}
