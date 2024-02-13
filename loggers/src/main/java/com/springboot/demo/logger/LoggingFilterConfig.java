package com.springboot.demo.logger;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoggingFilterConfig extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = requestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = responseWrapper(response);

        chain.doFilter(requestWrapper, responseWrapper);

        log.info("----------------------------------------------------------------------------------------------------");
        logRequest(requestWrapper);
        log.info("----------------------------------------------------------------------------------------------------");
        logResponse(responseWrapper);
        log.info("----------------------------------------------------------------------------------------------------");
    }

    private ContentCachingRequestWrapper requestWrapper(ServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            return (ContentCachingRequestWrapper) request;
        }
        return new ContentCachingRequestWrapper((HttpServletRequest) request);
    }

    private ContentCachingResponseWrapper responseWrapper(ServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper) {
            return (ContentCachingResponseWrapper) response;
        }
        return new ContentCachingResponseWrapper((HttpServletResponse) response);
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        StringBuilder builder = new StringBuilder();
//        builder.append("\nHeader: [" + getHeaders(Collections.list(request.getHeaderNames()), request::getHeader) + "]");
        builder.append("\nMethod: [" + request.getMethod() + "]");
        builder.append("\nURL: [" + request.getRequestURL() + "]");
        builder.append("\nParameters: [" + getParameters(Collections.list(request.getParameterNames()), request::getParameter) + "]");
//        builder.append("\nBody: [" + new String(request.getContentAsByteArray()) + "]");
        byte[] bytes = new byte[0];
        try {
            bytes = IOUtils.toByteArray(request.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        builder.append("\nBody: [" + new String(bytes) + "]");
        log.info("Log Request::: {}", builder);
    }

    private void logResponse(ContentCachingResponseWrapper response) throws IOException {
        StringBuilder builder = new StringBuilder();
//        builder.append("\nHeader: [" + getHeaders(response.getHeaderNames(), response::getHeader) + "]");
        builder.append("\nBody: [" + new String(response.getContentAsByteArray()) + "]");
        log.info("Log Response::: {}", builder);
        response.copyBodyToResponse();
    }

    private String getParameters(Collection<String> headerNames, Function<String, String> parameterValueResolver) {
        Map<String, String> parameters = new HashMap<>();
        for (String parameter : headerNames) {
            String parameterValue = parameterValueResolver.apply(parameter);
            parameters.put(parameter, parameterValue);
        }
        return parameters.toString().substring(1, parameters.toString().length() - 1);
    }

    private String getHeaders(Collection<String> headerNames, Function<String, String> headerValueResolver) {
        Map<String, String> headers = new HashMap<>();
        for (String headerName : headerNames) {
            String headerValue = headerValueResolver.apply(headerName);
            headers.put(headerName, headerValue);
        }
        return headers.toString().substring(1, headers.toString().length() - 1);
    }
}
