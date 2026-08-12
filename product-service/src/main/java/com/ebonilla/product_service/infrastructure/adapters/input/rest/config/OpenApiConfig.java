package com.ebonilla.product_service.infrastructure.adapters.input.rest.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("API REST servicio de productos")
                        .version("1.0.0")
                        .description("Documentacion de servicio de productos con proteccion de endpoints."))
                .addSecurityItem(new SecurityRequirement().
                        addList(securitySchemeName)) // Se configura para que swagger solicite a todos los endpoints el token autenticado
                .components(new Components() // Agregamos componentes
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme() // Agregamos un esquema de seguridad
                                        .name(securitySchemeName) // Nombre del esquema
                                        .type(SecurityScheme.Type.HTTP) // Se especifica el envio mediante las cabeceras HTTP
                                        .scheme("bearer") // Definimos el esquema HTTP especifico
                                        .bearerFormat("JWT"))); // Definimos el valor del esquema a enviar
    }
}
