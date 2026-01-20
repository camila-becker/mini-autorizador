package com.vr.miniautorizador.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransacaoRequest(

        @NotBlank(message = "O número do cartão é obrigatório.")
        String numeroCartao,

        @NotBlank(message = "A senha é obrigatória.")
        String senhaCartao,

        @NotNull(message = "O valor da transação é obrigatório.")
        @Positive
        BigDecimal valor

) { }
