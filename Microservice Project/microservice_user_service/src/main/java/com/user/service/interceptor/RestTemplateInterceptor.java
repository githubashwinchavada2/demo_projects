package com.user.service.interceptor;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_TYPE = "Bearer ";

    private OAuth2AuthorizedClientManager manager;

    public RestTemplateInterceptor(OAuth2AuthorizedClientManager manager) {
        this.manager = manager;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String token = manager
                .authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client").principal("internal").build())
                .getAccessToken().getTokenValue();
        log.info("Rest Template Interceptor token::: {}", token);
        request.getHeaders().add(AUTHORIZATION_HEADER, BEARER_TOKEN_TYPE + token);

        return execution.execute(request, body);
    }

}
