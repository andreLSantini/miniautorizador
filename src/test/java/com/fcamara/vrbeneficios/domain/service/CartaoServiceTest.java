package com.fcamara.vrbeneficios.domain.service;

import com.fcamara.vrbeneficios.adapter.output.database.data.Cartao;
import com.fcamara.vrbeneficios.domain.exception.CartaoExistenteException;
import com.fcamara.vrbeneficios.domain.exception.CartaoNaoEncontradoException;
import com.fcamara.vrbeneficios.domain.exception.SaldoCartaoInvalidoException;
import com.fcamara.vrbeneficios.domain.exception.SenhaInvalidaException;
import com.fcamara.vrbeneficios.domain.model.CartaoEntity;
import com.fcamara.vrbeneficios.port.output.CartaoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CartaoServiceTest {

    @Mock
    private CartaoPort cartaoPort;

    @InjectMocks
    private CartaoService cartaoService;

    private CartaoEntity cartaoEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartaoEntity = new CartaoEntity("6549873025634501", "1234");
    }

    @Test
    @DisplayName("Deve Criar um cartao com sucesso")
    void shouldCreateCardSuccess() {
        var response = cartaoService.execute(cartaoEntity);

        assertEquals(cartaoEntity.numeroCartao(), response.getNumeroCartao());
        assertEquals(cartaoEntity.senha(), response.getSenha());

        verify(cartaoPort).salvarCartao(any(Cartao.class));
    }

    @Test
    @DisplayName("Deve Lancar uma Exception de Cartao ja existente")
    void shouldThrowCartaoExistenteExceptionWhenCardExists() {
        when(cartaoPort.existsById(cartaoEntity.numeroCartao())).thenReturn(true);

        Executable executable = () -> cartaoService.execute(cartaoEntity);

        var exception = assertThrows(CartaoExistenteException.class, executable);
        assertEquals("Cartão já existe", exception.getMessage());
    }

    @Test
    @DisplayName("Deve Lancar uma Exception de Saldo Cartao Invalido")
    void shouldThrowSaldoCartaoInvalidoExceptionWhenSaldoIsInvalid() {
        when(cartaoPort.existsById(cartaoEntity.numeroCartao())).thenReturn(false);
        when(cartaoPort.salvarCartao(any(Cartao.class))).thenThrow(new SaldoCartaoInvalidoException("Saldo Invalido"));

        Executable executable = () -> cartaoService.execute(cartaoEntity);

        var exception = assertThrows(SaldoCartaoInvalidoException.class, executable);
        assertEquals("Saldo Invalido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve Obter Saldo com sucesso")
    void shouldGetBalanceSuccessfully() {
        String numeroCartao = cartaoEntity.numeroCartao();
        var cartao = new Cartao(UUID.randomUUID(), numeroCartao, "1234", BigDecimal.valueOf(100.00));
        when(cartaoPort.findByNumeroCartao(numeroCartao)).thenReturn(Optional.of(cartao));

        BigDecimal saldo = cartaoService.execute(numeroCartao);

        assertEquals(BigDecimal.valueOf(100.00), saldo);
    }

    @Test
    @DisplayName("Deve Lancar uma Exception de Cartao nao encontrado")
    void shouldThrowCartaoNaoEncontradoExceptionWhenCardNotFound() {
        when(cartaoPort.findByNumeroCartao(cartaoEntity.numeroCartao())).thenReturn(Optional.empty());

        Executable executable = () -> cartaoService.execute(cartaoEntity.numeroCartao());

        CartaoNaoEncontradoException exception = assertThrows(CartaoNaoEncontradoException.class, executable);
        assertEquals("", exception.getMessage());
    }

    @Test
    @DisplayName("Deve validar senha com sucesso")
    void shouldValidatePasswordSuccessfully() {
        var cartao = new Cartao(UUID.randomUUID(), cartaoEntity.numeroCartao(), cartaoEntity.senha(), BigDecimal.valueOf(100.00));
        assertDoesNotThrow(() -> cartaoService.validaSenhaCartao(cartao, cartaoEntity.senha()));
    }

    @Test
    @DisplayName("Deve Lancar uma Exception de senha invalida")
    void shouldThrowSenhaInvalidaExceptionWhenPasswordIsInvalid() {
        var cartao = new Cartao(UUID.randomUUID(), cartaoEntity.numeroCartao(), "senhaErrada", BigDecimal.valueOf(100.00));

        Executable executable = () -> cartaoService.validaSenhaCartao(cartao, cartaoEntity.senha());

        var exception = assertThrows(SenhaInvalidaException.class, executable);
        assertEquals("SENHA_INVALIDA", exception.getMessage());
    }
}