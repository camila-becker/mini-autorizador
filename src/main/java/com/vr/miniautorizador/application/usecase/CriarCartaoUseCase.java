package com.vr.miniautorizador.application.usecase;

import com.vr.miniautorizador.domain.model.entities.Cartao;
import com.vr.miniautorizador.domain.repository.CartaoRepository;

import java.util.Optional;

public class CriarCartaoUseCase {

    private final CartaoRepository cartaoRepository;

    public CriarCartaoUseCase(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public Optional<Cartao> buscarPorNumero(String numeroCartao) {
        return cartaoRepository.findByNumeroCartao(numeroCartao);
    }

    public Cartao criar(String numeroCartao, String senha) {
        return cartaoRepository.save(new Cartao(numeroCartao, senha));
    }
}
