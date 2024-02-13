package com.user.service.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.log4j.Log4j2;

@Configuration
@Component
@Log4j2
public class FeignClientInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_TYPE = "Bearer ";

    @Autowired
    private OAuth2AuthorizedClientManager manager;

    @Override
    public void apply(RequestTemplate template) {
        String token = manager
                .authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client").principal("internal").build())
                .getAccessToken().getTokenValue();
        log.info("Feign Client Interceptor token::: {}", token);

        template.header(AUTHORIZATION_HEADER, BEARER_TOKEN_TYPE + token);
    }

}
