package com.fcamara.vrbeneficios.port.input;

import java.math.BigDecimal;

public interface ObterSaldoCartaoUseCase {
    BigDecimal execute(String numeroCartao);
}
