package com.zair.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(info());
    }

    private Info info() {
        return new Info()
                .title("Cansebero Álbumes API")
                .description("API para consultar los álbumes y canciones del artista Canserbero")
                .termsOfService("https://github.com/ZairBulos/canserbero-albumes-api")
                .version("1.0.0")
                .contact(contact())
                .license(license());
    }

    private Contact contact() {
        return new Contact()
                .name("Zair Bulos")
                .email("buloszair@gmail.com")
                .url("https://www.linkedin.com/in/zair-bulos");
    }

    private License license() {
        return new License()
                .name("BSD-3-Clause license")
                .url("https://opensource.org/license/bsd-3-clause");
    }
}
