package com.itconsulting.mentalHealth.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean(name = "mentalHealth")
    public OpenAPI mentalHealthOpenApi() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Authorization", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("Bearer").bearerFormat("JWT")))
                .info(
                        new Info()
                                .title("mentalHealth App API")
                                .description("mentalHealth App API implemented with Spring Boot RESTful service and documented using springdoc-openapi and OpenAPI 3.0")
                                .version("1.0")

                );
    }
}
