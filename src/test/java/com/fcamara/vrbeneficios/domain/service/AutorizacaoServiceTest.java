package com.fcamara.vrbeneficios.domain.service;

import com.fcamara.vrbeneficios.adapter.output.database.data.Cartao;
import com.fcamara.vrbeneficios.domain.exception.CartaoNaoEncontradoException;
import com.fcamara.vrbeneficios.domain.exception.SenhaInvalidaException;
import com.fcamara.vrbeneficios.domain.model.TransacaoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AutorizacaoServiceTest {

    @Mock
    private CartaoService cartaoService;

    @InjectMocks
    private AutorizacaoService autorizacaoService;

    private TransacaoEntity transacaoEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transacaoEntity = new TransacaoEntity("6549873025634501", "1234", BigDecimal.valueOf(100.00));
    }

    @Test
    @DisplayName("Deve autorizar transação com sucesso")
    void shouldAuthorizeTransactionSuccessfully() {
        var cartao = new Cartao(UUID.randomUUID(), "6549873025634501", "1234", BigDecimal.valueOf(500.00));

        when(cartaoService.getCartao(transacaoEntity.numeroCartao())).thenReturn(Optional.of(cartao));

        autorizacaoService.execute(transacaoEntity);

        verify(cartaoService).validaSenhaCartao(cartao, transacaoEntity.senha());
        verify(cartaoService).salvarCartao(cartao);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o cartão não for encontrado")
    void shouldThrowExceptionWhenCardNotFound() {
        when(cartaoService.getCartao(transacaoEntity.numeroCartao())).thenReturn(Optional.empty());

        assertThrows(CartaoNaoEncontradoException.class, () -> autorizacaoService.execute(transacaoEntity));
    }

    @Test
    @DisplayName("Deve lançar exceção quando a senha for inválida")
    void shouldThrowExceptionWhenPasswordIsInvalid() {
        var cartao = new Cartao(UUID.randomUUID(), "6549873025634501", "1234", BigDecimal.valueOf(500.00));

        when(cartaoService.getCartao(transacaoEntity.numeroCartao())).thenReturn(Optional.of(cartao));
        doThrow(SenhaInvalidaException.class).when(cartaoService).validaSenhaCartao(cartao, transacaoEntity.senha());

        assertThrows(SenhaInvalidaException.class, () -> autorizacaoService.execute(transacaoEntity));
    }
}
