package com.vr.miniautorizador;

import com.vr.miniautorizador.application.service.CartaoService;
import com.vr.miniautorizador.application.service.TransacaoService;
import com.vr.miniautorizador.application.usecase.transacao.ResultadoTransacao;
import com.vr.miniautorizador.domain.model.entities.Cartao;
import com.vr.miniautorizador.interfaces.dto.CartaoRequest;
import com.vr.miniautorizador.interfaces.dto.TransacaoRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ConcorrenciaTransacaoTest {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private CartaoService cartaoService;

    @Test
    void deveEvitarSaldoNegativoComConcorrencia() throws Exception {

        Cartao cartao = cartaoService.criarCartao(new CartaoRequest("165165165165165165", "1234")).cartao();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<ResultadoTransacao> t1 = () -> transacaoService.autorizar(
                new TransacaoRequest(cartao.getNumeroCartao(), cartao.getSenha(), new BigDecimal("100.00"))
        );

        Callable<ResultadoTransacao> t2 = () -> transacaoService.autorizar(
                new TransacaoRequest(cartao.getNumeroCartao(), cartao.getSenha(), new BigDecimal("100.00"))
        );

        List<Future<ResultadoTransacao>> resultados = executor.invokeAll(List.of(t1, t2));

        int sucesso = 0;
        int falha = 0;

        for (Future<ResultadoTransacao> future : resultados) {
            try {
                ResultadoTransacao resultado = future.get();

                switch (resultado) {
                    case ResultadoTransacao.Ok ok -> sucesso++;
                    case ResultadoTransacao.SaldoInsuficiente erro -> falha++;
                    case ResultadoTransacao.SenhaInvalida erro -> falha++;
                    case ResultadoTransacao.CartaoInexistente erro -> falha++;
                    default -> falha++;
                }
            } catch (ExecutionException e) {
                if (e.getCause() instanceof ObjectOptimisticLockingFailureException) {
                    falha++;
                } else {
                    throw e;
                }
            }
        }

        cartaoService
                .obterSaldoCartao(cartao.getNumeroCartao())
                .ifPresent(value -> assertEquals(new BigDecimal("400.00"), value.getSaldo()));

        assertEquals(1, sucesso, "Apenas uma transação deve ser autorizada");
        assertEquals(1, falha, "A outra transação concorrente deve falhar");
    }
}
