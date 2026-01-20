package com.vr.miniautorizador.constants;

public class TestesCartaoConstantes {

    public static final String NUMERO_CARTAO = "1234567890123456";
    public static final String SENHA = "1234";
    public static final String PAYLOAD = """
            { "numeroCartao": "1234567890123456", "senha": "1234" }
    """;
    public static final String PAYLOAD_INVALIDO = """
            { "numeroCartao": "", "senha": "" }
    """;
}
