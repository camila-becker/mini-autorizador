package com.vr.miniautorizador.domain.model.rules;

import com.vr.miniautorizador.application.usecase.transacao.ResultadoTransacao;
import com.vr.miniautorizador.domain.model.entities.Cartao;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
public class RegraSaldoSuficiente implements RegraTransacao{

    @Override
    public ResultadoTransacao validar(Cartao cartao, String senha, BigDecimal valor) {
        log.info("RegraSaldoSuficiente ::: validar ::: Validando se cartão possui saldo insuficiente.");
        return Optional.of(cartao.getSaldo().compareTo(valor) >= 0)
                .filter(Boolean::booleanValue)
                .map(b -> (ResultadoTransacao) new ResultadoTransacao.Ok())
                .orElse(new ResultadoTransacao.SaldoInsuficiente());
    }

}
