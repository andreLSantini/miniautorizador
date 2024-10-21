package com.fcamara.vrbeneficios.adapter.output.database;

import com.fcamara.vrbeneficios.adapter.output.database.data.Cartao;
import com.fcamara.vrbeneficios.adapter.output.database.repository.CartaoRepository;
import com.fcamara.vrbeneficios.port.output.CartaoPort;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartaoPersistence implements CartaoPort {

    private final CartaoRepository cartaoRepository;

    @Transactional
    @Override
    public Cartao salvarCartao(Cartao cartao) {
        return cartaoRepository.save(cartao);
    }

    @Transactional
    @Override
    public boolean existsById(String numeroCartao) {
        return cartaoRepository.existsByNumeroCartao(numeroCartao);
    }

    @Transactional
    @Override
    public Optional<Cartao> findByNumeroCartao(String numeroCartao) {
        return cartaoRepository.findByNumeroCartao(numeroCartao);
    }
}
