package com.boletas20.boletas20.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(
            new Info()
            .title("API de Boletas - Shoes Shop")
            .version("2.0")
            .description("Esta API sirve para gestionar las boletas de la tienda de zapatillas, controlar los métodos de pago y organizar las opciones de envío.")
        );
    }
}

