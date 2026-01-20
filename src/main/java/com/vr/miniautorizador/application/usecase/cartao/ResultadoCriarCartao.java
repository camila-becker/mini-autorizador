package com.vr.miniautorizador.application.usecase.cartao;

import com.vr.miniautorizador.domain.model.entities.Cartao;

public sealed interface ResultadoCriarCartao permits ResultadoCriarCartao.Criado, ResultadoCriarCartao.JaExistente{

    Cartao cartao();
    record Criado(Cartao cartao) implements ResultadoCriarCartao {}
    record JaExistente(Cartao cartao) implements ResultadoCriarCartao {}

}
