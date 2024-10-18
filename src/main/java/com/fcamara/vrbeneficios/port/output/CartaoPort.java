package com.fcamara.vrbeneficios.port.output;

import com.fcamara.vrbeneficios.adapter.output.database.data.Cartao;
import com.fcamara.vrbeneficios.domain.model.CartaoEntity;

import java.util.Optional;

public interface CartaoPort {
    Cartao salvarCartao(Cartao cartao);

    boolean existsById(String numeroCartao);

    Optional<Cartao> findByNumeroCartao(String numeroCartao);
}
