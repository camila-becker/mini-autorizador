package com.vr.miniautorizador.infrastructure.repository;

import com.vr.miniautorizador.domain.model.entities.Cartao;
import com.vr.miniautorizador.domain.repository.CartaoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaoJpaRepository extends JpaRepository<Cartao, Long>, CartaoRepository {

    Optional<Cartao> findByNumeroCartao(String numeroCartao);

}
