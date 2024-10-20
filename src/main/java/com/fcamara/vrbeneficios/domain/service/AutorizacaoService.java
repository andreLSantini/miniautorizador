package com.fcamara.vrbeneficios.domain.service;

import com.fcamara.vrbeneficios.adapter.output.database.data.Cartao;
import com.fcamara.vrbeneficios.domain.exception.CartaoNaoEncontradoException;
import com.fcamara.vrbeneficios.domain.model.TransacaoEntity;
import com.fcamara.vrbeneficios.port.input.AutorizarTransacaoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutorizacaoService implements AutorizarTransacaoUseCase {

    @Autowired
    private CartaoService cartaoService;

    @Override
    public void execute(TransacaoEntity transacaoEntity) {
        var cartao = cartaoService.getCartao(transacaoEntity.numeroCartao())
                .orElseThrow(() -> new CartaoNaoEncontradoException("CARTAO_INEXISTENTE"));
        cartaoService.validaSenhaCartao(cartao, transacaoEntity.senha());

        cartao.debitarSaldo(transacaoEntity.valor());
        cartaoService.salvarCartao(cartao);
    }
}
