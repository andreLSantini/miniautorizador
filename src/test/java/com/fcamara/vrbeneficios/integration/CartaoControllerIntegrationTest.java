package com.fcamara.vrbeneficios.integration;

import com.fcamara.vrbeneficios.adapter.output.request.CriarCartaoRequest;
import com.fcamara.vrbeneficios.adapter.output.response.CartaoCriadoResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class CartaoControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String numeroCartao;

    @BeforeEach
    public void setUp() {
        numeroCartao = String.valueOf(new Random().nextInt());
    }

    @Test
    @DisplayName("Deve Criar um cartao com sucesso, integracao")
    public void shouldCreateCardSuccessIntegration() {
        var cartaoRequest = CriarCartaoRequest.builder()
                .numeroCartao(numeroCartao)
                .senha("1234")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CriarCartaoRequest> request = new HttpEntity<>(cartaoRequest, headers);

        ResponseEntity<CartaoCriadoResponse> response = this.testRestTemplate
                .withBasicAuth("usuario", "123456")
                .exchange("/cartoes", HttpMethod.POST, request, CartaoCriadoResponse.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
        assertEquals(numeroCartao, response.getBody().getNumeroCartao());
        assertEquals("1234", response.getBody().getSenha());
    }

    @Test
    @DisplayName("Deve Lancar uma exception cartao ja existente, integracao")
    public void shouldExceptionCreateCardIntegration() {
        var cartaoRequest = CriarCartaoRequest.builder()
                .numeroCartao(numeroCartao)
                .senha("1234")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CriarCartaoRequest> request = new HttpEntity<>(cartaoRequest, headers);

        this.testRestTemplate
                .withBasicAuth("usuario", "123456")
                .exchange("/cartoes", HttpMethod.POST, request, CartaoCriadoResponse.class);

        ResponseEntity<CartaoCriadoResponse> response = this.testRestTemplate
                .withBasicAuth("usuario", "123456")
                .exchange("/cartoes", HttpMethod.POST, request, CartaoCriadoResponse.class);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    @DisplayName("Deve Obter saldo com sucesso, integracao")
    public void shouldGetBalanceCardIntegration() {
        var cartaoRequest = CriarCartaoRequest.builder()
                .numeroCartao("6549873025634501")
                .senha("1234")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var createRequest = new HttpEntity<>(cartaoRequest, headers);

        ResponseEntity<CartaoCriadoResponse> createResponse = this.testRestTemplate
                .withBasicAuth("usuario", "123456")
                .exchange("/cartoes", HttpMethod.POST, createRequest, CartaoCriadoResponse.class);

        assertEquals(HttpStatus.OK, createResponse.getStatusCode());

        String numeroCartao = createResponse.getBody().getNumeroCartao();

        ResponseEntity<BigDecimal> saldoResponse = this.testRestTemplate
                .withBasicAuth("usuario", "123456")
                .exchange("/cartoes/" + numeroCartao, HttpMethod.GET, null, BigDecimal.class);

        assertEquals(HttpStatus.OK, saldoResponse.getStatusCode());
        assertEquals(0, saldoResponse.getBody().compareTo(BigDecimal.valueOf(500.00)));
    }

    @Test
    @DisplayName("Deve retornar 404 ao tentar obter saldo de cartão inexistente, integração")
    public void shouldReturn404WhenCardNotFoundIntegration() {
        String numeroCartaoInexistente = "1234567890123456";
        ResponseEntity<String> response = this.testRestTemplate
                .withBasicAuth("usuario", "123456")
                .exchange("/cartoes/" + numeroCartaoInexistente, HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


}
