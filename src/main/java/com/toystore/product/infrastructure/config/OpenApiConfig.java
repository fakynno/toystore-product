package com.toystore.product.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
            title = "ToyStore API",
            version = "1.0.0",
            description = "API para Gestão de Produtos da Loja de Brinquedos"
    )
)
public class OpenApiConfig {
}
