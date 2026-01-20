package com.vr.miniautorizador.application.usecase;

import com.vr.miniautorizador.domain.model.entities.Cartao;
import com.vr.miniautorizador.domain.repository.CartaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.vr.miniautorizador.constants.TestesCartaoConstantes.NUMERO_CARTAO;
import static com.vr.miniautorizador.constants.TestesCartaoConstantes.SENHA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CriarCartaoUseCaseTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private CriarCartaoUseCase useCase;

    @Test
    void deveRetornarCartaoQuandoBuscarPorNumeroExistente() {

        Cartao cartao = new Cartao(NUMERO_CARTAO, SENHA);
        when(cartaoRepository.findByNumeroCartao(NUMERO_CARTAO)).thenReturn(Optional.of(cartao));

        Optional<Cartao> resultado = useCase.buscarPorNumero(NUMERO_CARTAO);

        assertTrue(resultado.isPresent());
        assertEquals(NUMERO_CARTAO, resultado.get().getNumeroCartao());
        verify(cartaoRepository).findByNumeroCartao(NUMERO_CARTAO);

    }

    @Test
    void deveRetornarOptionalVazioQuandoBuscarPorNumeroInexistente() {

        when(cartaoRepository.findByNumeroCartao(NUMERO_CARTAO)).thenReturn(Optional.empty());

        Optional<Cartao> resultado = useCase.buscarPorNumero(NUMERO_CARTAO);

        assertTrue(resultado.isEmpty());
        verify(cartaoRepository).findByNumeroCartao(NUMERO_CARTAO);

    }

    @Test
    void deveCriarCartaoChamandoSaveNoRepositorio() {

        Cartao cartao = new Cartao(NUMERO_CARTAO, SENHA);
        when(cartaoRepository.save(any(Cartao.class))).thenReturn(cartao);

        Cartao resultado = useCase.criar(NUMERO_CARTAO, SENHA);

        assertEquals(NUMERO_CARTAO, resultado.getNumeroCartao());
        assertEquals(SENHA, resultado.getSenha());
        verify(cartaoRepository).save(any(Cartao.class));

    }
}