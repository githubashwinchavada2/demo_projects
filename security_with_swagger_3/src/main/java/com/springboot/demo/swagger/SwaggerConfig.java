package com.springboot.demo.swagger;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Demo Project of Swagger",
                description = "This is Swagger Project",
                termsOfService = "http://localhost:8080",
                contact = @Contact(name = "Ashwin Chavada Company", url = "Ashwin Chavada Company URL", email = "ashwinchavada2@gmail.com"),
                license = @License(name = "License of API", url = "API license URL"),
                version = "2.0")
        , security = @SecurityRequirement(name = "basicAuth") // this enables basic auth at global level (for all APIs in the project)
//        and can be applied at method level and class level with @SecurityRequirement(name = "basicAuth")
        )
@SecurityScheme(
        name = "basicAuth",
        scheme = "basic",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER)
public class SwaggerConfig {

}
