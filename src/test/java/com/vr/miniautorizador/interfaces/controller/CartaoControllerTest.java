package com.vr.miniautorizador.interfaces.controller;

import com.vr.miniautorizador.application.mapper.CartaoMapper;
import com.vr.miniautorizador.application.service.CartaoService;
import com.vr.miniautorizador.application.usecase.ResultadoCriarCartao;
import com.vr.miniautorizador.domain.model.entities.Cartao;
import com.vr.miniautorizador.interfaces.dto.CartaoRequest;
import com.vr.miniautorizador.interfaces.dto.CartaoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.stream.Stream;

import static com.vr.miniautorizador.constants.TestesCartaoConstantes.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CartaoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CartaoService service;

    @Mock
    private CartaoMapper mapper;

    @InjectMocks
    private CartaoController controller;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @ParameterizedTest
    @MethodSource("cenariosValidos")
    void deveRetornarStatusEsperado(ResultadoCriarCartao resultado, int statusEsperado, Cartao cartao,
                                    CartaoResponse response, Class<?> tipoEsperado) throws Exception {

        CartaoRequest request = new CartaoRequest(NUMERO_CARTAO, SENHA);
        when(service.criarCartao(request)).thenReturn(resultado);
        when(mapper.toResponse(cartao)).thenReturn(response);

        ResultadoCriarCartao resultadoReal = service.criarCartao(request);
        assertInstanceOf(tipoEsperado, resultadoReal);

        mockMvc.perform(post("/cartoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(PAYLOAD))
                .andExpect(status().is(statusEsperado))
                .andExpect(jsonPath("$.numeroCartao").value(NUMERO_CARTAO))
                .andExpect(jsonPath("$.senha").value(SENHA));



    }

    @Test
    void deveRetornar400QuandoRequestInvalido() throws Exception {

        mockMvc.perform(
                        post("/cartoes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(PAYLOAD_INVALIDO))
                .andExpect(status().isBadRequest());

    }

    static Stream<Arguments> cenariosValidos() {

        Cartao cartao = new Cartao(NUMERO_CARTAO, SENHA);
        CartaoResponse response = new CartaoResponse(NUMERO_CARTAO, SENHA);

        return Stream.of(
                Arguments.of(new ResultadoCriarCartao.Criado(cartao), 201, cartao, response,
                        ResultadoCriarCartao.Criado.class),
                Arguments.of(new ResultadoCriarCartao.JaExistente(cartao), 422, cartao, response,
                        ResultadoCriarCartao.JaExistente.class)
        );
    }
}