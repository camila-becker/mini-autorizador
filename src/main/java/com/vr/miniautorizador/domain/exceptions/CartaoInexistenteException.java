package com.vr.miniautorizador.domain.exceptions;

public class CartaoInexistenteException extends RuntimeException {
    public CartaoInexistenteException() {
        super("CARTAO_INEXISTENTE");
    }
}
