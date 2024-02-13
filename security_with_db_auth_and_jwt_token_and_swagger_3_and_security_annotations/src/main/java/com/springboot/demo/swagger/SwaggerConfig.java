package com.springboot.demo.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Demo Project of Swagger",
                description = "This is Swagger Project",
                termsOfService = "http://localhost:8080",
                contact = @Contact(name = "Ashwin Chavada Company", url = "Ashwin Chavada Company URL", email = "ashwinchavada2@gmail.com"),
                license = @License(name = "License of API", url = "API license URL"),
                version = "2.0")
        )
//@SecurityScheme(
//        name = "bearerAuth",
//        scheme = "bearer",
//        bearerFormat = "JWT",
//        type = SecuritySchemeType.HTTP,
//        in = SecuritySchemeIn.HEADER
//      )
//@SecurityRequirement(name = "JWT Token Authentication")
//use above commented @SecurityScheme and @SecurityRequirement annotations to apply bearer authentication
//and use @SecurityRequirement(name = "bearerAuth") at method level and class level
public class SwaggerConfig {

// use below commented openAPI() method to apply bearer authentication at global level
// and no need of @SecurityRequirement(name = "bearerAuth") at method level or class level
// use @SecurityRequirements annotations to remove bearer auth at class level or method level
    @Bean
    public OpenAPI openAPI() {
        final String name = "bearerAuth";

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(name))
                .components(new Components()
                        .addSecuritySchemes(name, new SecurityScheme()
                                .name(name)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .type(SecurityScheme.Type.HTTP)
                                .in(SecurityScheme.In.HEADER)));
    }
}
