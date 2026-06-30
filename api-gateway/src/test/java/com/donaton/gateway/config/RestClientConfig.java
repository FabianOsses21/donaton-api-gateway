package com.donaton.gateway.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;

class RestClientConfigTest {

    @Test
    void deberiaCrearRestClient() {
        RestClientConfig config = new RestClientConfig();

        RestClient client = config.restClient();

        assertNotNull(client);
    }
}