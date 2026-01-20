package com.vr.miniautorizador.domain.model.entities;

import com.vr.miniautorizador.domain.exceptions.SaldoInsuficienteException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroCartao;

    private String senha;

    private BigDecimal saldo;

    @Version
    private Long version;

    public Cartao(String numeroCartao, String senha) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.saldo = new BigDecimal("500.00");
    }

    public void debitar(BigDecimal valor) {

        if(valor.compareTo(this.saldo) > 0) {
            throw new SaldoInsuficienteException();
        }

        this.saldo = this.saldo.subtract(valor);
    }

}
