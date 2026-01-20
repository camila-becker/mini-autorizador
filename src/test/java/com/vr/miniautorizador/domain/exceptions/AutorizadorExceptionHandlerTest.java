package com.vr.miniautorizador.domain.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AutorizadorExceptionHandlerTest {

    private final AutorizadorExceptionHandler handler = new AutorizadorExceptionHandler();

    @Test
    void deveRetornarMapaDeErrosQuandoValidacaoFalhar() {

        FieldError fieldError = new FieldError("transacaoRequest", "valor", "Campo obrigatório");
        BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "transacaoRequest");
        bindingResult.addError(fieldError);

        MethodParameter methodParameter = new MethodParameter(AutorizadorExceptionHandler.class .getDeclaredMethods()[0],
                0);

        MethodArgumentNotValidException ex =
                new MethodArgumentNotValidException(methodParameter, bindingResult);

        ResponseEntity<Map<String, String>> response = handler.handleException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Campo obrigatório", response.getBody().get("valor"));

    }
}