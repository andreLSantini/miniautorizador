package com.fcamara.vrbeneficios.adapter.output.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoRequest {
    private String numeroCartao;
    private String senha;
    private BigDecimal valor;
}
