package com.greenatom.clientselfservice.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class PayService {
    private final RestTemplate restTemplate;
    public void pay(UriComponentsBuilder builder) {
        restTemplate.exchange(builder.toUriString(),
                              HttpMethod.POST,
                   null,
                              Void.class);
    }
}
