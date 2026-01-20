package com.vr.miniautorizador.interfaces.controller;

import com.vr.miniautorizador.application.mapper.CartaoMapper;
import com.vr.miniautorizador.application.service.CartaoService;
import com.vr.miniautorizador.application.usecase.cartao.ResultadoCriarCartao;
import com.vr.miniautorizador.interfaces.dto.CartaoRequest;
import com.vr.miniautorizador.interfaces.dto.CartaoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final CartaoService service;
    private final CartaoMapper mapper;

    public CartaoController(CartaoService service, CartaoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Criar novo cartão", description = "Cria um cartão com saldo inicial de R$500,00")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criação com sucesso"),
            @ApiResponse(responseCode = "422", description = "Cartão já existe"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação")
    })
    @PostMapping()
    public ResponseEntity<CartaoResponse> criarCartao(@Valid @RequestBody CartaoRequest request) {

        ResultadoCriarCartao resultado = service.criarCartao(request);

        return switch (resultado) {
            case ResultadoCriarCartao.Criado criado ->
                    ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(criado.cartao()));

            case ResultadoCriarCartao.JaExistente existente ->
                    ResponseEntity.status(HttpStatus.valueOf(422)).body(mapper.toResponse(existente.cartao()));
        };

    }

    @Operation(summary = "Obter saldo do cartão", description = "Retorna o saldo disponível do cartão informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtenção com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cartão não existe"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação")
    })
    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> obterSaldoCartao(@PathVariable("numeroCartao") String numeroCartao) {

      return service.obterSaldoCartao(numeroCartao)
                .map(cartao -> ResponseEntity.ok(cartao.getSaldo()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

}
