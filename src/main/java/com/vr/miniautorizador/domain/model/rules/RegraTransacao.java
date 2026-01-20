package com.vr.miniautorizador.domain.model.rules;

import com.vr.miniautorizador.application.usecase.transacao.ResultadoTransacao;
import com.vr.miniautorizador.domain.model.entities.Cartao;

import java.math.BigDecimal;

@FunctionalInterface
public interface RegraTransacao {
    ResultadoTransacao validar(Cartao cartao, String senha, BigDecimal valor);
}
