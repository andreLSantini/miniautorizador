package com.fcamara.vrbeneficios.adapter.input.controller.integration;

import com.fcamara.vrbeneficios.adapter.output.request.CriarCartaoRequest;
import com.fcamara.vrbeneficios.adapter.output.response.CartaoCriadoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartaoControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void teste() {
        var cartaoRequest = CriarCartaoRequest.builder()
                .numeroCartao("6549873025634501")
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
        assertEquals("6549873025634501", response.getBody().getNumeroCartao());
        assertEquals("1234", response.getBody().getSenha());
    }

}
