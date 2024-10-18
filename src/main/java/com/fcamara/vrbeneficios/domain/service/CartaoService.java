package com.fcamara.vrbeneficios.domain.service;

import com.fcamara.vrbeneficios.adapter.output.database.data.Cartao;
import com.fcamara.vrbeneficios.adapter.output.response.CartaoCriadoResponse;
import com.fcamara.vrbeneficios.domain.exception.CartaoExistenteException;
import com.fcamara.vrbeneficios.domain.exception.CartaoNaoEncontradoException;
import com.fcamara.vrbeneficios.domain.exception.SaldoCartaoInvalidoException;
import com.fcamara.vrbeneficios.domain.model.CartaoEntity;
import com.fcamara.vrbeneficios.port.input.CriarCartaoUseCase;
import com.fcamara.vrbeneficios.port.input.ObterSaldoCartaoUseCase;
import com.fcamara.vrbeneficios.port.output.CartaoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartaoService implements CriarCartaoUseCase, ObterSaldoCartaoUseCase {

    private final CartaoPort cartaoPort;

    @Override
    public CartaoCriadoResponse execute(CartaoEntity cartaoEntity) {
        if (cartaoPort.existsById(cartaoEntity.numeroCartao())) {
            throw new CartaoExistenteException("Cartão já existe");
        }
        var novoCartao = getNovoCartao(cartaoEntity);
        cartaoPort.salvarCartao(novoCartao);
        return CartaoCriadoResponse.builder().numeroCartao(cartaoEntity.numeroCartao()).senha(cartaoEntity.senha()).build();
    }

    private static Cartao getNovoCartao(CartaoEntity cartaoEntity) {
        var cartaoNovo = new Cartao(
                UUID.randomUUID(),
                cartaoEntity.numeroCartao(),
                cartaoEntity.senha(),
                BigDecimal.valueOf(500.00)
        );
        if (cartaoNovo.getSaldo().equals(BigDecimal.valueOf(500.00))) {
            return cartaoNovo;
        } else {
            throw new SaldoCartaoInvalidoException("Saldo Invalido");
        }
    }

    @Override
    public BigDecimal execute(String numeroCartao) {
        Cartao cartao = cartaoPort.findByNumeroCartao(numeroCartao)
                .orElseThrow(() -> new CartaoNaoEncontradoException("Cartão não encontrado"));
        return cartao.getSaldo();
    }
}
