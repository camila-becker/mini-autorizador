package com.vr.miniautorizador.application.usecase;

import com.vr.miniautorizador.domain.model.entities.Cartao;
import com.vr.miniautorizador.domain.model.rules.RegraCartaoExistente;
import com.vr.miniautorizador.domain.model.rules.RegraSaldoSuficiente;
import com.vr.miniautorizador.domain.model.rules.RegraSenhaValida;
import com.vr.miniautorizador.domain.model.rules.RegraTransacao;
import com.vr.miniautorizador.domain.repository.CartaoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class TransacaoUseCase {

    private final CartaoRepository cartaoRepository;
    private final List<RegraTransacao> regras;

    public TransacaoUseCase(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
        this.regras = List.of(
                new RegraCartaoExistente(),
                new RegraSenhaValida(),
                new RegraSaldoSuficiente()
        );
    }

    public ResultadoTransacao autorizar(String numeroCartao, String senhaCartao, BigDecimal valor) {

        Optional<Cartao> cartaoOpt = cartaoRepository.findByNumeroCartao(numeroCartao);

        ResultadoTransacao resultado = regras.stream()
                .map(r -> r.validar(cartaoOpt.orElse(null), senhaCartao, valor))
                .filter(r -> !(r instanceof ResultadoTransacao.Ok))
                .findFirst() .orElse(new ResultadoTransacao.Ok());

        cartaoOpt.ifPresent(c -> {
            if (resultado instanceof ResultadoTransacao.Ok) {
                c.debitar(valor);
                cartaoRepository.save(c);
            }
        });

        return resultado;

    }
}
