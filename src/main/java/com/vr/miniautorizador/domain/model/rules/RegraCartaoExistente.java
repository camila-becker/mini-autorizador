package com.vr.miniautorizador.domain.model.rules;

import com.vr.miniautorizador.application.usecase.transacao.ResultadoTransacao;
import com.vr.miniautorizador.domain.model.entities.Cartao;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
public class RegraCartaoExistente implements RegraTransacao{

    @Override
    public ResultadoTransacao validar(Cartao cartao, String senha, BigDecimal valor) {
        log.info("RegraCartaoExistente ::: validar ::: Validando se cartão já existe.");
        return Optional.ofNullable(cartao)
                .<ResultadoTransacao>map(c -> new ResultadoTransacao.Ok())
                .orElse(new ResultadoTransacao.CartaoInexistente());
    }

}
