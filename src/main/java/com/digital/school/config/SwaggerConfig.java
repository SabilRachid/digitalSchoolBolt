package com.digital.school.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI digitalSchoolOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Digital School API")
                .description("API de gestion scolaire")
                .version("1.0.0"));
    }

}

