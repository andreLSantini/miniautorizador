package com.fcamara.vrbeneficios.port.input;

import com.fcamara.vrbeneficios.adapter.output.response.CartaoCriadoResponse;
import com.fcamara.vrbeneficios.domain.model.CartaoEntity;

public interface CriarCartaoUseCase {
    CartaoCriadoResponse execute(CartaoEntity cartaoEntity);
}
