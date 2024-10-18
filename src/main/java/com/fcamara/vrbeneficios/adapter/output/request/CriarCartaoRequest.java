package com.fcamara.vrbeneficios.adapter.output.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriarCartaoRequest {
    private String numeroCartao;
    private String senha;
}
