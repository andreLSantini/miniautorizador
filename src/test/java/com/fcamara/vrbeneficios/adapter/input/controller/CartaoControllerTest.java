package com.fcamara.vrbeneficios.adapter.input.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CartaoController.class)
class CartaoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CartaoController cartaoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartaoController).build();
    }

    @Test
    @DisplayName("")
    void test() {
        assertEquals(1,1);
    }

    @Test
    @DisplayName("")
    void shouldCreateCardSuccess() {
        assertEquals(1,1);
    }

    @Test
    @DisplayName("")
    void shouldCreateCardFail() {
        assertEquals(1,1);
    }

    @Test
    @DisplayName("")
    void shouldGetBalanceSuccess() {
        assertEquals(1,1);
    }

    @Test
    @DisplayName("")
    void shouldGetBalanceFail() {
        assertEquals(1,1);
    }


}