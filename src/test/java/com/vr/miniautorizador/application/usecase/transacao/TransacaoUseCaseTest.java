package com.vr.miniautorizador.application.usecase.transacao;

import com.vr.miniautorizador.domain.model.entities.Cartao;
import com.vr.miniautorizador.domain.repository.CartaoRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static com.vr.miniautorizador.constants.TestesCartaoConstantes.NUMERO_CARTAO;
import static com.vr.miniautorizador.constants.TestesCartaoConstantes.SENHA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransacaoUseCaseTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private TransacaoUseCase useCase;

    @ParameterizedTest
    @MethodSource("cenariosTransacao")
    void deveTestarCenariosDeTransacao(Optional<Cartao> cartaoOpt,
                                       String senhaInformada,
                                       BigDecimal valor,
                                       Class<? extends ResultadoTransacao> resultadoEsperado,
                                       BigDecimal saldoEsperado) {

        when(cartaoRepository.findByNumeroCartao(NUMERO_CARTAO)).thenReturn(cartaoOpt);

        ResultadoTransacao resultado = useCase.autorizar(NUMERO_CARTAO, senhaInformada, valor);

        assertInstanceOf(resultadoEsperado, resultado);

        cartaoOpt.ifPresent(c -> {
            if (saldoEsperado != null) {
                assertEquals(0, c.getSaldo().compareTo(saldoEsperado));
            }
        });
    }


    static Stream<Arguments> cenariosTransacao() {
        return Stream.of(
                Arguments.of(Optional.of(new Cartao(NUMERO_CARTAO, SENHA)), SENHA, BigDecimal.valueOf(100.00),
                        ResultadoTransacao.Ok.class, BigDecimal.valueOf(400.00)),

                Arguments.of(Optional.of(new Cartao(NUMERO_CARTAO, SENHA)), "12345", BigDecimal.valueOf(100.00),
                        ResultadoTransacao.SenhaInvalida.class, BigDecimal.valueOf(500.00)),

                Arguments.of(Optional.empty(), SENHA, BigDecimal.valueOf(100.00),
                        ResultadoTransacao.CartaoInexistente.class, null),

                Arguments.of(Optional.of(new Cartao(NUMERO_CARTAO, SENHA) {{
                    setSaldo(BigDecimal.ZERO);
                }}), SENHA, BigDecimal.valueOf(100.00), ResultadoTransacao.SaldoInsuficiente.class, BigDecimal.ZERO)
        );
    }
}