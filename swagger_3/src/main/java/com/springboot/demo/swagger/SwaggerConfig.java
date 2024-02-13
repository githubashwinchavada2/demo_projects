package com.springboot.demo.swagger;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Demo Project of Swagger",
                description = "This is Swagger Project",
                termsOfService = "http://localhost:8080",
                contact = @Contact(name = "Ashwin Chavada Company", url = "Ashwin Chavada Company URL", email = "ashwinchavada2@gmail.com"),
                license = @License(name = "License of API", url = "API license URL"),
                version = "2.0"))
public class SwaggerConfig {

}
