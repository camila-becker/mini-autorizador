package com.vr.miniautorizador.infrastructure.repository;

import com.vr.miniautorizador.domain.model.entities.Cartao;
import com.vr.miniautorizador.domain.repository.CartaoRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoJpaRepository extends JpaRepository<Cartao, Long>, CartaoRepository {

    Optional<Cartao> findByNumeroCartao(String numeroCartao);

}
