package com.vr.miniautorizador.interfaces.controller;

import com.vr.miniautorizador.application.service.TransacaoService;
import com.vr.miniautorizador.application.usecase.transacao.ResultadoTransacao;
import com.vr.miniautorizador.domain.enums.ResultadoTransacaoEnum;
import com.vr.miniautorizador.interfaces.dto.TransacaoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transacoes")
public class TransacaoController {

    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @Operation(summary = "Realizar uma transação", description = "Autoriza ou rejeita uma transação de pagamento com cartão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK"),
            @ApiResponse(responseCode = "422", description = "Transação não autorizada"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação")
    })
    @PostMapping()
    public ResponseEntity<String> realizarTransacao(@Valid @RequestBody TransacaoRequest request) {

        ResultadoTransacao resultado = service.autorizar(request);

        return switch (resultado) {
            case ResultadoTransacao.Ok ok ->
                    ResponseEntity.status(HttpStatus.CREATED).body(ResultadoTransacaoEnum.OK.name());
            case ResultadoTransacao.CartaoInexistente inex ->
                    ResponseEntity.status(422).body(ResultadoTransacaoEnum.CARTAO_INEXISTENTE.name());
            case ResultadoTransacao.SenhaInvalida invalida ->
                    ResponseEntity.status(422).body(ResultadoTransacaoEnum.SENHA_INVALIDA.name());
            case ResultadoTransacao.SaldoInsuficiente insuf ->
                    ResponseEntity.status(422).body(ResultadoTransacaoEnum.SALDO_INSUFICIENTE.name());
        };

    }

}
