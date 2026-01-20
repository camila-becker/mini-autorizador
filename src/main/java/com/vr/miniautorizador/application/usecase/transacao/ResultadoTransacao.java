package com.vr.miniautorizador.application.usecase.transacao;

public sealed interface ResultadoTransacao
        permits ResultadoTransacao.Ok,
        ResultadoTransacao.CartaoInexistente,
        ResultadoTransacao.SenhaInvalida,
        ResultadoTransacao.SaldoInsuficiente {

    record Ok() implements ResultadoTransacao {}
    record CartaoInexistente() implements ResultadoTransacao {}
    record SenhaInvalida() implements ResultadoTransacao {}
    record SaldoInsuficiente() implements ResultadoTransacao {}

}
