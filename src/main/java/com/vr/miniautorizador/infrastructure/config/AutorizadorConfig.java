package com.vr.miniautorizador.infrastructure.config;

import com.vr.miniautorizador.application.usecase.CartaoUseCase;
import com.vr.miniautorizador.application.usecase.TransacaoUseCase;
import com.vr.miniautorizador.domain.repository.CartaoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutorizadorConfig {

    @Bean
    CartaoUseCase cartaoUseCase(CartaoRepository cartaoRepository) {
        return new CartaoUseCase(cartaoRepository);
    }

    @Bean
    TransacaoUseCase transacaoUseCase(CartaoRepository cartaoRepository) {
        return new TransacaoUseCase(cartaoRepository);
    }
}
