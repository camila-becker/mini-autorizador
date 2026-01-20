package com.vr.miniautorizador.infrastructure.config;

import com.vr.miniautorizador.application.usecase.CriarCartaoUseCase;
import com.vr.miniautorizador.domain.repository.CartaoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutorizadorConfig {

    @Bean
    CriarCartaoUseCase criarCartaoUseCase(CartaoRepository cartaoRepository) {
        return new CriarCartaoUseCase(cartaoRepository);
    }
}
