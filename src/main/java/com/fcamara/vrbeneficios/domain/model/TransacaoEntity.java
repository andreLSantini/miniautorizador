package com.fcamara.vrbeneficios.domain.model;

import java.math.BigDecimal;

public record TransacaoEntity(String numeroCartao, String senha, BigDecimal valor) {
}
