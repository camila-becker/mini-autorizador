package com.vr.miniautorizador.domain.repository;

import com.vr.miniautorizador.domain.model.entities.Cartao;

import java.util.Optional;

public interface CartaoRepository {
    Optional<Cartao> findByNumeroCartao(String numero);
    Cartao save(Cartao cartao);
}
