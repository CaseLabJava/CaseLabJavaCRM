package com.greenatom.clientselfservice;

import com.greenatom.clientselfservice.utils.interceptor.BearerAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ClientSelfServiceApplication {

    private final BearerAuthInterceptor bearerAuthInterceptor;

    @Autowired
    public ClientSelfServiceApplication(BearerAuthInterceptor bearerAuthInterceptor) {
        this.bearerAuthInterceptor = bearerAuthInterceptor;
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientSelfServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        restTemplate.getInterceptors().add(bearerAuthInterceptor);
        return restTemplate;
    }
}
