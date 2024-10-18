package com.fcamara.vrbeneficios.port.output;

import com.fcamara.vrbeneficios.adapter.output.database.data.Cartao;
import com.fcamara.vrbeneficios.domain.model.CartaoEntity;

public interface CartaoPort {
    Cartao salvarCartao(Cartao cartao);
}
