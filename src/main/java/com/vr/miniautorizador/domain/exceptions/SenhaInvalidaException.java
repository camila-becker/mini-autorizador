package com.vr.miniautorizador.domain.exceptions;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
        super("SENHA_INVALIDA");
    }
}
