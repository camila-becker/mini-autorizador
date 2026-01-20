package com.vr.miniautorizador.application.service;

import com.vr.miniautorizador.application.usecase.transacao.ResultadoTransacao;
import com.vr.miniautorizador.application.usecase.transacao.TransacaoUseCase;
import com.vr.miniautorizador.interfaces.dto.TransacaoRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.vr.miniautorizador.constants.TestesCartaoConstantes.NUMERO_CARTAO;
import static com.vr.miniautorizador.constants.TestesCartaoConstantes.SENHA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    private TransacaoUseCase transacaoUseCase;

    @InjectMocks
    private TransacaoService service;

    @Test
    void deveAutorizarTransacao() {

        TransacaoRequest request = new TransacaoRequest(NUMERO_CARTAO, SENHA, BigDecimal.valueOf(10.00));
        ResultadoTransacao esperado = new ResultadoTransacao.Ok();

        when(transacaoUseCase.autorizar(request.numeroCartao(), request.senhaCartao(), request.valor()))
                .thenReturn(esperado);

        ResultadoTransacao resultado = service.autorizar(request);

        assertInstanceOf(ResultadoTransacao.Ok.class, resultado);
        assertEquals(esperado, resultado);
        verify(transacaoUseCase).autorizar(NUMERO_CARTAO, SENHA, BigDecimal.valueOf(10.00));

    }
}