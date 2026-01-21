package com.vr.miniautorizador.application.service;

import com.vr.miniautorizador.application.usecase.transacao.ResultadoTransacao;
import com.vr.miniautorizador.application.usecase.transacao.TransacaoUseCase;
import com.vr.miniautorizador.interfaces.dto.TransacaoRequest;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class TransacaoService {

    private final TransacaoUseCase transacaoUseCase;

    public TransacaoService(TransacaoUseCase transacaoUseCase) {
        this.transacaoUseCase = transacaoUseCase;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResultadoTransacao autorizar(TransacaoRequest request) {
        try {
            log.info("TransacaoService ::: autorizar ::: Iniciando processo para autorizar transação no cartão.");
            return transacaoUseCase.autorizar(request.numeroCartao(), request.senhaCartao(), request.valor());
        } catch (ObjectOptimisticLockingFailureException | OptimisticLockException e) {
            return new ResultadoTransacao.SaldoInsuficiente();
        }
    }

}
