package com.vr.miniautorizador.domain.model.rules;

import com.vr.miniautorizador.application.usecase.ResultadoTransacao;
import com.vr.miniautorizador.domain.model.entities.Cartao;

import java.math.BigDecimal;
import java.util.Optional;

public class RegraSenhaValida implements RegraTransacao{

    @Override
    public ResultadoTransacao validar(Cartao cartao, String senha, BigDecimal valor) {
        return Optional.of(cartao.getSenha().equals(senha))
                .filter(Boolean::booleanValue)
                .map(b -> (ResultadoTransacao) new ResultadoTransacao.Ok())
                .orElse(new ResultadoTransacao.SenhaInvalida());

    }

}
