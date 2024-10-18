package com.fcamara.vrbeneficios.adapter.output.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoCriadoResponse {
    private String numeroCartao;
    private String senha;
}
