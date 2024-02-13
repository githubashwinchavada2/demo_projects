package com.user.service.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import com.user.service.interceptor.RestTemplateInterceptor;

import lombok.AllArgsConstructor;

@Configuration
@PropertySource({
    "classpath:/application.yml",
//    "classpath:/application-${spring.profiles.active}.yml"
/**    uncomment above line to read data from active profile (YML) if you have multiple YML files */
    })
/** Above annotation is used to read YML files */
@AllArgsConstructor
public class AppConfig {

    private ClientRegistrationRepository clientRegistrationRepository;
    private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setInterceptors(Arrays.asList(new RestTemplateInterceptor(manager())));

        return restTemplate;
    }

    @Bean
    public OAuth2AuthorizedClientManager manager() {
        OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();

        DefaultOAuth2AuthorizedClientManager clientManager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, oAuth2AuthorizedClientRepository);
        clientManager.setAuthorizedClientProvider(provider);

        return clientManager;
    }
}
