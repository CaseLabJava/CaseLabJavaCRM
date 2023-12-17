package com.greenatom.clientselfservice.contoller;

import com.greenatom.clientselfservice.domain.dto.client.ClientResponseDTO;
import com.greenatom.clientselfservice.domain.entity.Client;
import com.greenatom.clientselfservice.restTemplate.ClientRestTemplate;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("self_service_client")
public class ClientController {

    private final ClientRestTemplate clientRestTemplate;

    @GetMapping("/id")
    public ResponseEntity<ClientResponseDTO> getById(){
        Claims claims = (Claims) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return ResponseEntity.ok(clientRestTemplate.getOneById(Long.valueOf((Integer) claims.get("client_id"))));
    }
}
