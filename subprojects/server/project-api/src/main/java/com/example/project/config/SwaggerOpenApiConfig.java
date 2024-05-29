package com.example.project.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerOpenApiConfig {
    @Bean
    public OpenAPI publicApi() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("Project API")
                    .description("Project API")
                    .version("v1")
            );
    }
}
