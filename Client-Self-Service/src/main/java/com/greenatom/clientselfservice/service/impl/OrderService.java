package com.greenatom.clientselfservice.service.impl;

import com.greenatom.clientselfservice.domain.dto.order.OrderRequestDTO;
import com.greenatom.clientselfservice.domain.dto.order.OrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RestTemplate restTemplate;

    public OrderResponseDTO createDraft(String name, OrderRequestDTO orderRequestDTO) {
        String url = getUrl("/draft");
        OrderResponseDTO orderResponseDTO = restTemplate.postForObject(url, orderRequestDTO, OrderResponseDTO.class);
        return orderResponseDTO;
    }

    private String getUrl (String action){
        return "http://CaseLabJavaCrm/api/orders" + action;
    }
}
