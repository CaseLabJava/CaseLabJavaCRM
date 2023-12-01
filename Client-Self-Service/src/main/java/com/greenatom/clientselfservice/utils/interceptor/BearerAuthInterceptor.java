package com.greenatom.clientselfservice.utils.interceptor;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BearerAuthInterceptor implements ClientHttpRequestInterceptor {

    private final String accessToken;

    @Autowired
    public BearerAuthInterceptor(@Value("${jwt_technical_user}") String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    @NonNull
    public ClientHttpResponse intercept(HttpRequest request, @NonNull byte[] body, ClientHttpRequestExecution execution) throws IOException {
        org.springframework.http.HttpHeaders headers = request.getHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.setBearerAuth(accessToken);
        return execution.execute(request,body);
    }
}
