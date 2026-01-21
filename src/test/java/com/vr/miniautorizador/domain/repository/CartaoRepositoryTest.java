package com.vr.miniautorizador.domain.repository;

import com.vr.miniautorizador.domain.model.entities.Cartao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class CartaoRepositoryTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private CartaoRepository cartaoRepository;

    @Test
    void deveSalvarERecuperarCartao() {
        Cartao cartao = new Cartao("1234567890123456", "1234");
        cartaoRepository.save(cartao);

        Optional<Cartao> encontrado = cartaoRepository.findByNumeroCartao("1234567890123456");
        assertTrue(encontrado.isPresent());
        assertEquals("1234", encontrado.get().getSenha());
    }

}