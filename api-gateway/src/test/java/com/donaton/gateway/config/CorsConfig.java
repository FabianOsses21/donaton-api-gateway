package com.donaton.gateway.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CorsConfigTest {

    @Test
    void deberiaCrearConfiguracionCors() {
        CorsConfig config = new CorsConfig();

        assertNotNull(config);
    }
}