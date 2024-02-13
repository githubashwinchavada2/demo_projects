package com.springboot.demo.swagger;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Collections.singletonList(securityContexts()))
                .securitySchemes(Collections.singletonList((new BasicAuth("basicAuth"))))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.springboot.demo"))
//                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Demo Project of Swagger")
                .description("This is Swagger Project")
                .termsOfServiceUrl("http://localhost:8080")
                .contact(new Contact("Ashwin Chavada Company", "Ashwin Chavada Company URL","ashwinchavada2@gmail.com"))
                .license("License of API")
                .licenseUrl("API license URL")
                .version("1.0")
                .build();
    }

    private SecurityContext securityContexts() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("basicAuth", scopes())))
                .build();
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[] {};
    }
}
