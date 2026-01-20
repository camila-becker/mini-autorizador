package com.vr.miniautorizador.domain.exceptions;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException() {
        super("SALDO_INSUFICIENTE");
    }
}
