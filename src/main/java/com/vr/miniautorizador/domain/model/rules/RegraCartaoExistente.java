package com.vr.miniautorizador.domain.model.rules;

import com.vr.miniautorizador.application.usecase.ResultadoTransacao;
import com.vr.miniautorizador.domain.model.entities.Cartao;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class RegraCartaoExistente implements RegraTransacao{

    @Override
    public ResultadoTransacao validar(Cartao cartao, String senha, BigDecimal valor) {
        return Optional.ofNullable(cartao)
                .<ResultadoTransacao>map(c -> new ResultadoTransacao.Ok())
                .orElse(new ResultadoTransacao.CartaoInexistente());
    }

}
