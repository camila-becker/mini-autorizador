package com.vr.miniautorizador.application.service;

import com.vr.miniautorizador.application.usecase.cartao.CartaoUseCase;
import com.vr.miniautorizador.application.usecase.cartao.ResultadoCriarCartao;
import com.vr.miniautorizador.domain.model.entities.Cartao;
import com.vr.miniautorizador.interfaces.dto.CartaoRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CartaoService {

    private final CartaoUseCase cartaoUseCase;

    public CartaoService(CartaoUseCase cartaoUseCase) {
        this.cartaoUseCase = cartaoUseCase;
    }

    @Transactional
    public ResultadoCriarCartao criarCartao(CartaoRequest request) {
        log.info("CartaoService ::: criarCartao ::: Iniciando processo para criar o cartão.");

        return cartaoUseCase.buscarPorNumero(request.numeroCartao())
                .<ResultadoCriarCartao>map(ResultadoCriarCartao.JaExistente::new)
                .orElseGet(() -> {
                    Cartao novo = cartaoUseCase.criar(request.numeroCartao(), request.senha());
                    return new ResultadoCriarCartao.Criado(novo);
                });

    }

    public Optional<Cartao> obterSaldoCartao(String numeroCartao) {
       log.info("CartaoService ::: obterSaldoCartao ::: Iniciando processo para obter saldo do cartão.");
       return cartaoUseCase.buscarPorNumero(numeroCartao);
    }

}
