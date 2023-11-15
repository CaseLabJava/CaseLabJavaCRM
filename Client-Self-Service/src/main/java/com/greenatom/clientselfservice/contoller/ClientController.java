package com.greenatom.clientselfservice.contoller;

import com.greenatom.clientselfservice.domain.entity.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final RestTemplate restTemplate;

}
