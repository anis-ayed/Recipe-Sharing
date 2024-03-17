package com.ayed.recipe_sharing.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition()
public class SwaggerConfig {
  @Bean
  public OpenAPI swaggerApiConfig() {
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
        .components(
            new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
        .info(
            new Info()
                .title("Recipe Sharing REST API")
                .description("Recipe Sharing Spring Boot Project SwaggerUI integration.")
                .version("1.0")
                .contact(
                    new Contact()
                        .name("Anis Ayed")
                        .email("www.recipe-sharing.com")
                        .url("anis@test.com"))
                .license(new License().name("License of API").url("API license URL")));
  }

  private SecurityScheme createAPIKeyScheme() {
    return new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer");
  }
}
