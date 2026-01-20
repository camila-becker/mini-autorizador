package com.vr.miniautorizador.application.service;

import com.vr.miniautorizador.application.usecase.CriarCartaoUseCase;
import com.vr.miniautorizador.application.usecase.ResultadoCriarCartao;
import com.vr.miniautorizador.domain.model.entities.Cartao;
import com.vr.miniautorizador.interfaces.dto.CartaoRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static com.vr.miniautorizador.constants.TestesCartaoConstantes.NUMERO_CARTAO;
import static com.vr.miniautorizador.constants.TestesCartaoConstantes.SENHA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartaoServiceTest {

    @Mock
    private CriarCartaoUseCase cartaoUseCase;

    @InjectMocks
    private CartaoService service;

    @ParameterizedTest
    @MethodSource("cenariosCriarCartao")
    void deveTestarCriacaoOuExistenciaDoCartao(Optional<Cartao> existente, Cartao retorno, Class<?> tipoEsperado,
                                               boolean deveSalvar) {

        CartaoRequest request = new CartaoRequest(NUMERO_CARTAO, SENHA);

        when(cartaoUseCase.buscarPorNumero(NUMERO_CARTAO)).thenReturn(existente);

        if (deveSalvar) {
            when(cartaoUseCase.criar(anyString(), anyString())).thenReturn(retorno);
        }

        ResultadoCriarCartao resultado = service.criarCartao(request);

        assertInstanceOf(tipoEsperado, resultado);
        assertEquals(NUMERO_CARTAO, resultado.cartao().getNumeroCartao());

        if (deveSalvar) {
            verify(cartaoUseCase).criar(anyString(), anyString());
        } else {
            verify(cartaoUseCase, never()).criar(anyString(), anyString());
        }

    }

    @Test
    void deveRetornarOSaldoDoCartao() {

        Cartao cartao = new Cartao(NUMERO_CARTAO, SENHA);

        when(cartaoUseCase.buscarPorNumero(NUMERO_CARTAO)).thenReturn(Optional.of(cartao));

        Optional<Cartao> resultado = service.obterSaldoCartao(NUMERO_CARTAO);

        if(resultado.isPresent()) {
            assertNotNull(resultado.get());
            assertEquals(cartao.getSaldo(), resultado.get().getSaldo());
        }

        verify(cartaoUseCase).buscarPorNumero(anyString());

    }

    static Stream<Arguments> cenariosCriarCartao() {

        Cartao novoCartao = new Cartao(NUMERO_CARTAO, SENHA);
        Cartao existente = new Cartao(NUMERO_CARTAO, SENHA);

        return Stream.of(
                Arguments.of(Optional.empty(), novoCartao, ResultadoCriarCartao.Criado.class, true),
                Arguments.of(Optional.of(existente), existente, ResultadoCriarCartao.JaExistente.class, false)
        );

    }
}