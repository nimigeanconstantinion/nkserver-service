package com.example.nkserver.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri:http://localhost:8080/realms/rsk}")
    private String issuerUri;

    @Bean
    public OpenAPI bookManagementOpenAPI() {
        SecurityScheme bearerAuthScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization")
                .description("Enter JWT Bearer token **_only_** (without 'Bearer' prefix)");

        SecurityScheme oauth2Scheme = new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(new OAuthFlows()
                        .password(new OAuthFlow()
                                .tokenUrl(issuerUri + "/protocol/openid-connect/token")
                                .refreshUrl(issuerUri + "/protocol/openid-connect/token")));

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearer-auth")
                .addList("oauth2");

        return new OpenAPI()
                .info(new Info()
                        .title("Book Management API")
                        .version("1.0.0")
                        .description("Documented CRUD operations for managing books with Keycloak authentication.\n\n" +
                                "**How to authenticate:**\n" +
                                "1. Get a token from Keycloak (see below)\n" +
                                "2. Click 'Authorize' button\n" +
                                "3. Paste the token in the 'Value' field\n" +
                                "4. Click 'Authorize'\n\n" +
                                "**Get Token URL:** " + issuerUri + "/protocol/openid-connect/token"))
                .servers(List.of(
                        new Server().url("http://localhost:8082").description("Local server")))
                .components(new Components()
                        .addSecuritySchemes("bearer-auth", bearerAuthScheme)
                        .addSecuritySchemes("oauth2", oauth2Scheme))
                .addSecurityItem(securityRequirement);
    }
}