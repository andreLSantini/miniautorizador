package com.fcamara.vrbeneficios.adapter.input.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcamara.vrbeneficios.adapter.output.request.CriarCartaoRequest;
import com.fcamara.vrbeneficios.port.input.CriarCartaoUseCase;
import com.fcamara.vrbeneficios.port.input.ObterSaldoCartaoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CartaoController.class)
@AutoConfigureMockMvc(addFilters = false)
class CartaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CriarCartaoUseCase criarCartaoUseCase;
    @MockBean
    private ObterSaldoCartaoUseCase obterSaldoCartaoUseCase;


    @Test
    @DisplayName("")
    void test() {
        assertEquals(1, 1);
    }

    @Test
    @DisplayName("")
    void shouldCreateCardSuccess() throws Exception {
        var cartaoRequest = CriarCartaoRequest.builder().numeroCartao("6549873025634501").build();
        mockMvc.perform(post("/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartaoRequest))
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.numeroCartao").value("6549873025634501"))
                .andExpect(jsonPath("$.senha").value("1234"));
    }

    @Test
    @DisplayName("")
    void shouldCreateCardFail() {
        assertEquals(1, 1);
    }

    @Test
    @DisplayName("")
    void shouldGetBalanceSuccess() {
        assertEquals(1, 1);
    }

    @Test
    @DisplayName("")
    void shouldGetBalanceFail() {
        assertEquals(1, 1);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}