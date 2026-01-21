package com.vr.miniautorizador.application.usecase.cartao;

import com.vr.miniautorizador.domain.model.entities.Cartao;
import com.vr.miniautorizador.domain.repository.CartaoRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class CartaoUseCase {

    private final CartaoRepository cartaoRepository;

    public CartaoUseCase(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public Optional<Cartao> buscarPorNumero(String numeroCartao) {
        log.info("CartaoUseCase ::: buscarPorNumero ::: Obter dados do cartão através do número {}.",
                numeroCartao);
        return cartaoRepository.findByNumeroCartao(numeroCartao);
    }

    public Cartao criar(String numeroCartao, String senha) {
        log.info("CartaoUseCase ::: criar ::: Criar cartão.");
        return cartaoRepository.save(new Cartao(numeroCartao, senha));
    }
}
