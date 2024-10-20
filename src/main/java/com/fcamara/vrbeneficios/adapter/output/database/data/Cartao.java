package com.fcamara.vrbeneficios.adapter.output.database.data;


import com.fcamara.vrbeneficios.domain.exception.SaldoInsuficienteException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "cartao")
@NoArgsConstructor
@AllArgsConstructor
public class Cartao {
    @Id
    @Column
    private UUID idCartao;
    @Column
    private String numeroCartao;
    @Column
    private String senha;
    @Column
    private BigDecimal saldo;

    public void debitarSaldo(BigDecimal valor) {
        if (this.saldo.compareTo(valor) >= 0) {
            this.saldo = this.saldo.subtract(valor);
        } else {
            throw new SaldoInsuficienteException("SALDO_INSUFICIENTE");
        }
    }
}
