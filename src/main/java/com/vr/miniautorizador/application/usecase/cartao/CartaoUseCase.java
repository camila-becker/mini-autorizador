package com.vr.miniautorizador.application.usecase.cartao;

import com.vr.miniautorizador.domain.model.entities.Cartao;
import com.vr.miniautorizador.domain.repository.CartaoRepository;

import java.util.Optional;

public class CartaoUseCase {

    private final CartaoRepository cartaoRepository;

    public CartaoUseCase(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public Optional<Cartao> buscarPorNumero(String numeroCartao) {
        return cartaoRepository.findByNumeroCartao(numeroCartao);
    }

    public Cartao criar(String numeroCartao, String senha) {
        return cartaoRepository.save(new Cartao(numeroCartao, senha));
    }
}
