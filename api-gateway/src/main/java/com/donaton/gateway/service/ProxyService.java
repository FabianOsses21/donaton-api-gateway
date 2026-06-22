package com.donaton.gateway.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;

import java.util.Enumeration;

@Service
@RequiredArgsConstructor
public class ProxyService {

    private final RestClient restClient;

    public ResponseEntity<String> forward(
            HttpMethod method,
            String targetUrl,
            String body,
            HttpServletRequest request) {

        HttpHeaders headers = copyHeaders(request);

        try {
            ResponseEntity<String> response = restClient
                    .method(method)
                    .uri(targetUrl)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .body(body == null ? "" : body)
                    .retrieve()
                    .toEntity(String.class);

            return ResponseEntity
                    .status(response.getStatusCode())
                    .headers(cleanResponseHeaders(response.getHeaders()))
                    .body(response.getBody());

        } catch (HttpStatusCodeException exception) {
            return ResponseEntity
                    .status(exception.getStatusCode())
                    .headers(cleanResponseHeaders(exception.getResponseHeaders()))
                    .body(exception.getResponseBodyAsString());

        } catch (Exception exception) {
            String detalle = exception.getMessage() == null
                    ? "Error desconocido"
                    : exception.getMessage().replace("\"", "'");

            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("{\"error\":\"No se pudo conectar con el microservicio destino\",\"detalle\":\""
                            + detalle + "\"}");
        }
    }

    private HttpHeaders copyHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();

            if (headerName.equalsIgnoreCase("host") ||
                    headerName.equalsIgnoreCase("content-length")) {
                continue;
            }

            Enumeration<String> values = request.getHeaders(headerName);
            while (values.hasMoreElements()) {
                headers.add(headerName, values.nextElement());
            }
        }

        return headers;
    }

    private HttpHeaders cleanResponseHeaders(HttpHeaders originalHeaders) {
    HttpHeaders headers = new HttpHeaders();

    if (originalHeaders == null) {
        return headers;
    }

    originalHeaders.forEach((key, values) -> {
        if (!key.equalsIgnoreCase("transfer-encoding") &&
                !key.equalsIgnoreCase("connection") &&
                !key.equalsIgnoreCase("access-control-allow-origin") &&
                !key.equalsIgnoreCase("access-control-allow-methods") &&
                !key.equalsIgnoreCase("access-control-allow-headers") &&
                !key.equalsIgnoreCase("access-control-allow-credentials")) {
            headers.put(key, values);
        }
    });

    return headers;
}
}
