package com.donaton.gateway;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ApiGatewayApplicationTests {

    @Test
    void deberiaCargarClasePrincipal() {
        ApiGatewayApplication app = new ApiGatewayApplication();

        assertNotNull(app);
    }
}