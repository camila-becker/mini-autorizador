package com.vr.miniautorizador.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CartaoRequest(
        @NotBlank(message = "O número do cartão é obrigatório.")
        String numeroCartao,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 4, max = 6, message = "A senha deve ter entre 4 e 6 caracteres.")
        String senha
) { }
