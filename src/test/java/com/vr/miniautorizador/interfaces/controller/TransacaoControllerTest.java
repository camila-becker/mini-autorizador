package com.vr.miniautorizador.interfaces.controller;

import com.vr.miniautorizador.application.service.TransacaoService;
import com.vr.miniautorizador.application.usecase.transacao.ResultadoTransacao;
import com.vr.miniautorizador.domain.enums.ResultadoTransacaoEnum;
import com.vr.miniautorizador.interfaces.dto.TransacaoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TransacaoControllerTest {


    private MockMvc mockMvc;

    @Mock
    private TransacaoService service;

    @InjectMocks
    private TransacaoController controller;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @ParameterizedTest
    @MethodSource("cenariosTransacao")
    void deveTestarTodosCenariosDeTransacao(ResultadoTransacao resultadoMock,
                                            int statusEsperado,
                                            String corpoEsperado) throws Exception {

        when(service.autorizar(any(TransacaoRequest.class))).thenReturn(resultadoMock);

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "numeroCartao": "123456789",
                              "senhaCartao": "1234",
                              "valor": 100.00
                            }
                        """))
                .andExpect(status().is(statusEsperado))
                .andExpect(content().string(corpoEsperado));
    }


    static Stream<Arguments> cenariosTransacao() {
        return Stream.of(
                Arguments.of(new ResultadoTransacao.Ok(), HttpStatus.CREATED.value(),
                        ResultadoTransacaoEnum.OK.name()),
                Arguments.of(new ResultadoTransacao.CartaoInexistente(), 422,
                        ResultadoTransacaoEnum.CARTAO_INEXISTENTE.name()),
                Arguments.of(new ResultadoTransacao.SenhaInvalida(), 422,
                        ResultadoTransacaoEnum.SENHA_INVALIDA.name()),
                Arguments.of(new ResultadoTransacao.SaldoInsuficiente(), 422,
                        ResultadoTransacaoEnum.SALDO_INSUFICIENTE.name())
        );
    }
}