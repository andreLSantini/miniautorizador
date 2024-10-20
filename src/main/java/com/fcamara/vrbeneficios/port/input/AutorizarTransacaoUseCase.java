package com.fcamara.vrbeneficios.port.input;

import com.fcamara.vrbeneficios.domain.model.TransacaoEntity;

public interface AutorizarTransacaoUseCase {

    void execute(TransacaoEntity transacaoEntity);
}
