package com.vr.miniautorizador.application.service;

import com.vr.miniautorizador.application.usecase.transacao.ResultadoTransacao;
import com.vr.miniautorizador.application.usecase.transacao.TransacaoUseCase;
import com.vr.miniautorizador.interfaces.dto.TransacaoRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    private final TransacaoUseCase transacaoUseCase;

    public TransacaoService(TransacaoUseCase transacaoUseCase) {
        this.transacaoUseCase = transacaoUseCase;
    }

    @Transactional
    public ResultadoTransacao autorizar(TransacaoRequest request) {
        return transacaoUseCase.autorizar(request.numeroCartao(), request.senhaCartao(), request.valor());
    }

}
