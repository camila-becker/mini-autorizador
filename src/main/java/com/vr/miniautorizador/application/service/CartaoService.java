package com.vr.miniautorizador.application.service;

import com.vr.miniautorizador.application.usecase.CriarCartaoUseCase;
import com.vr.miniautorizador.application.usecase.ResultadoCriarCartao;
import com.vr.miniautorizador.domain.model.entities.Cartao;
import com.vr.miniautorizador.interfaces.dto.CartaoRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartaoService {

    private final CriarCartaoUseCase criarCartaoUseCase;

    public CartaoService(CriarCartaoUseCase criarCartaoUseCase) {
        this.criarCartaoUseCase = criarCartaoUseCase;
    }

    @Transactional
    public ResultadoCriarCartao criarCartao(CartaoRequest request) {

        return criarCartaoUseCase.buscarPorNumero(request.numeroCartao())
                .<ResultadoCriarCartao>map(ResultadoCriarCartao.JaExistente::new)
                .orElseGet(() -> {
                    Cartao novo = criarCartaoUseCase.criar(request.numeroCartao(), request.senha());
                    return new ResultadoCriarCartao.Criado(novo);
                });

    }

    public Optional<Cartao> obterSaldoCartao(String numeroCartao) {
       return criarCartaoUseCase.buscarPorNumero(numeroCartao);
    }

}
