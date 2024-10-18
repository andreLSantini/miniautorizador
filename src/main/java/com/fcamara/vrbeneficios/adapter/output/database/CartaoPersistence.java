package com.fcamara.vrbeneficios.adapter.output.database;

import com.fcamara.vrbeneficios.adapter.output.database.data.Cartao;
import com.fcamara.vrbeneficios.adapter.output.database.repository.CartaoRepository;
import com.fcamara.vrbeneficios.port.output.CartaoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartaoPersistence implements CartaoPort {

    private final CartaoRepository cartaoRepository;

    @Override
    public Cartao salvarCartao(Cartao cartao) {
        return cartaoRepository.save(cartao);
    }

    @Override
    public boolean existsById(String numeroCartao) {
        return cartaoRepository.existsByNumeroCartao(numeroCartao);
    }

    @Override
    public Optional<Cartao> findByNumeroCartao(String numeroCartao) {
        return cartaoRepository.findByNumeroCartao(numeroCartao);
    }
}
