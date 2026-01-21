package com.vr.miniautorizador.domain.model.rules;

import com.vr.miniautorizador.application.usecase.transacao.ResultadoTransacao;
import com.vr.miniautorizador.domain.model.entities.Cartao;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
public class RegraSenhaValida implements RegraTransacao{

    @Override
    public ResultadoTransacao validar(Cartao cartao, String senha, BigDecimal valor) {
        log.info("RegraSenhaValida ::: validar ::: Validando senha do cartão.");
        return Optional.of(cartao.getSenha().equals(senha))
                .filter(Boolean::booleanValue)
                .map(b -> (ResultadoTransacao) new ResultadoTransacao.Ok())
                .orElse(new ResultadoTransacao.SenhaInvalida());

    }

}
