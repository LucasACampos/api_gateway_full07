package com.example.ApiGatewayExercicio.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Objects;

@Component
public class CommandLineRunnerKongConfig implements CommandLineRunner {

    private final RestTemplate restTemplate;
    private final String kongUrl;

    private final Logger logger;

    public CommandLineRunnerKongConfig(
            @Value("${kong.api.config.url}") String kongUrl
    ) {
        this.kongUrl = kongUrl;
        this.logger = LoggerFactory.getLogger(this.getClass().getName());

        Duration duration = Duration.ofSeconds(3);

        this.restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(duration)
                .setReadTimeout(duration)
                .build();
    }

    @Override
    public void run(String... args) {

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            ResponseEntity<RequestResponseData> gatewayResponse = restTemplate.postForEntity(
                    kongUrl + "/services",
                    new HttpEntity<>(
                            """
                                    {
                                      "name": "LucasCamposPedidosService",
                                      "tags": null,
                                      "protocol": "http",
                                      "path": null,
                                      "read_timeout": 60000,
                                      "retries": 5,
                                      "host": "localhost",
                                      "connect_timeout": 60000,
                                      "ca_certificates": null,
                                      "client_certificate": null,
                                      "write_timeout": 60000,
                                      "port": 8080
                                    }""",
                            httpHeaders
                    ),
                    RequestResponseData.class
            );

            restTemplate.postForEntity(
                    kongUrl + "/routes",
                    new HttpEntity<>(
                            String.format(
                                    """
                                            {
                                               "service": {
                                                 "id": "%s"
                                               },
                                               "name": "LucasCamposPedidosApiRoute",
                                               "paths": [
                                                 "/lucas-campos-pedidos"
                                               ],
                                               "snis": null,
                                               "hosts": null,
                                               "methods": [
                                                 "GET",
                                                 "PUT",
                                                 "POST",
                                                 "DELETE"
                                               ],
                                               "headers": null,
                                               "sources": null,
                                               "destinations": null,
                                               "tags": [],
                                               "regex_priority": 0,
                                               "path_handling": "v0",
                                               "strip_path": true,
                                               "preserve_host": false,
                                               "https_redirect_status_code": 426,
                                               "protocols": [
                                                 "http"
                                               ],
                                               "request_buffering": true,
                                               "response_buffering": true
                                             }
                                            """,
                                    Objects.requireNonNull(gatewayResponse.getBody()).getId()
                            )
                            ,
                            httpHeaders
                    ),
                    RequestResponseData.class
            );

            ResponseEntity<RequestResponseData> consumerResponse = restTemplate.postForEntity(
                    kongUrl + "/consumers",
                    new HttpEntity<>(
                            """
                                    {
                                      "username": "LucasCamposConsumer",
                                      "custom_id": "LucasCamposConsumer",
                                      "tags": []
                                    }
                                    """
                            ,
                            httpHeaders
                    ),
                    RequestResponseData.class
            );

            restTemplate.postForEntity(
                    kongUrl + "/plugins",
                    new HttpEntity<>(
                            String.format(
                                    """
                                            {
                                              "enabled": true,
                                              "name": "basic-auth",
                                              "service": {"id": "%s"},
                                              "instance_name": "LucasCamposBasicAuthPlugin",
                                              "tags": null,
                                              "protocols": [
                                                "grpc",
                                                "grpcs",
                                                "http",
                                                "https",
                                                "ws",
                                                "wss"
                                              ]
                                            }
                                            """,
                                    gatewayResponse.getBody().getId()
                            )
                            ,
                            httpHeaders
                    ),
                    RequestResponseData.class
            );

            restTemplate.postForEntity(
                    kongUrl + "/consumers/{consumerId}/basic-auth",
                    new HttpEntity<>(
                            String.format(
                                    """
                                            {
                                              "consumer": "%s",
                                              "password": "admin",
                                              "tags": [
                                                "admin"
                                              ],
                                              "username": "admin"
                                            }
                                            """,
                                    Objects.requireNonNull(consumerResponse.getBody()).getId()
                            )
                            ,
                            httpHeaders
                    ),
                    RequestResponseData.class,
                    consumerResponse.getBody().getId()
                    );
        } catch (Exception e) {
            logger.error("Erro ao configurar kong durante inicialização");
        }

        logger.info("Kong configurado com sucesso :)");

    }

    private static class RequestResponseData {
        private String id;

        public RequestResponseData(String id) {
            this.id = id;
        }

        public RequestResponseData() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "RequestResponseData{" +
                    "id='" + id + '\'' +
                    '}';
        }
    }
}
