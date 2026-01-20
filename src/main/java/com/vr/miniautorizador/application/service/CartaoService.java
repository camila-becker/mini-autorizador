package com.vr.miniautorizador.application.service;

import com.vr.miniautorizador.application.usecase.CartaoUseCase;
import com.vr.miniautorizador.application.usecase.ResultadoCriarCartao;
import com.vr.miniautorizador.domain.model.entities.Cartao;
import com.vr.miniautorizador.interfaces.dto.CartaoRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartaoService {

    private final CartaoUseCase cartaoUseCase;

    public CartaoService(CartaoUseCase cartaoUseCase) {
        this.cartaoUseCase = cartaoUseCase;
    }

    @Transactional
    public ResultadoCriarCartao criarCartao(CartaoRequest request) {

        return cartaoUseCase.buscarPorNumero(request.numeroCartao())
                .<ResultadoCriarCartao>map(ResultadoCriarCartao.JaExistente::new)
                .orElseGet(() -> {
                    Cartao novo = cartaoUseCase.criar(request.numeroCartao(), request.senha());
                    return new ResultadoCriarCartao.Criado(novo);
                });

    }

    public Optional<Cartao> obterSaldoCartao(String numeroCartao) {
       return cartaoUseCase.buscarPorNumero(numeroCartao);
    }

}
