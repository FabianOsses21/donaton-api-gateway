package com.donaton.gateway.controller;

import com.donaton.gateway.service.ProxyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GatewayController {

    private final ProxyService proxyService;

    @Value("${services.necesidades.url}")
    private String necesidadesUrl;

    @Value("${services.donaciones.url}")
    private String donacionesUrl;

    @Value("${services.logistica.url}")
    private String logisticaUrl;

    @RequestMapping(
            value = {
                    "/api/v1/necesidades",
                    "/api/v1/necesidades/**"
            },
            method = {
                    RequestMethod.GET,
                    RequestMethod.POST,
                    RequestMethod.PUT,
                    RequestMethod.PATCH,
                    RequestMethod.DELETE
            }
    )
    public ResponseEntity<String> necesidades(
            HttpMethod method,
            @RequestBody(required = false) String body,
            HttpServletRequest request) {

        String targetUrl = buildTargetUrl(necesidadesUrl, request);
        return proxyService.forward(method, targetUrl, body, request);
    }

    @RequestMapping(
            value = {
                    "/api/v1/donaciones",
                    "/api/v1/donaciones/**"
            },
            method = {
                    RequestMethod.GET,
                    RequestMethod.POST,
                    RequestMethod.PUT,
                    RequestMethod.PATCH,
                    RequestMethod.DELETE
            }
    )
    public ResponseEntity<String> donaciones(
            HttpMethod method,
            @RequestBody(required = false) String body,
            HttpServletRequest request) {

        String targetUrl = buildTargetUrl(donacionesUrl, request);
        return proxyService.forward(method, targetUrl, body, request);
    }

    @RequestMapping(
            value = {
                    "/api/v1/logistica",
                    "/api/v1/logistica/**"
            },
            method = {
                    RequestMethod.GET,
                    RequestMethod.POST,
                    RequestMethod.PUT,
                    RequestMethod.PATCH,
                    RequestMethod.DELETE
            }
    )
    public ResponseEntity<String> logistica(
            HttpMethod method,
            @RequestBody(required = false) String body,
            HttpServletRequest request) {

        String targetUrl = buildTargetUrl(logisticaUrl, request);
        return proxyService.forward(method, targetUrl, body, request);
    }

    @GetMapping("/")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("{\"status\":\"API Gateway Donaton funcionando\",\"puerto\":8080}");
    }

    private String buildTargetUrl(String serviceUrl, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String queryString = request.getQueryString();

        String targetUrl = serviceUrl + requestUri;

        if (queryString != null && !queryString.isBlank()) {
            targetUrl += "?" + queryString;
        }

        return targetUrl;
    }
}
