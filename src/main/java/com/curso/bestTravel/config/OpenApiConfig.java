package com.curso.bestTravel.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
info = @Info
        (title = "Best travel API",
                version = "1.0",
                description = "Documentation for endpoints in best Travel")
)
public class OpenApiConfig {
}
