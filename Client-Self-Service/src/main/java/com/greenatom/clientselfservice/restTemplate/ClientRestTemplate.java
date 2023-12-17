package com.greenatom.clientselfservice.restTemplate;


import com.greenatom.clientselfservice.domain.dto.client.ClientResponseDTO;
import com.greenatom.clientselfservice.domain.dto.security.ClientRegistrationDTO;
import com.greenatom.clientselfservice.domain.entity.Client;
import com.greenatom.clientselfservice.utils.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientRestTemplate {

    private final RestTemplate restTemplate;

    public Optional<Client> getOneByEmail(String email) {

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder.fromUriString(getUrl("/email"))
                .queryParam("email",email);

        ParameterizedTypeReference<Optional<Client>> responseType = new ParameterizedTypeReference<>() {};
        return restTemplate.exchange(componentsBuilder.toUriString(), HttpMethod.GET,null,responseType).getBody();
    }

    public ClientResponseDTO save(ClientRegistrationDTO clientRegistrationDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ClientRegistrationDTO> request = new HttpEntity<>(clientRegistrationDTO,headers);
        return restTemplate.postForObject(getUrl("/add"), request, ClientResponseDTO.class);
    }

    public ClientResponseDTO getOneById(Long id) {
        try {
            return restTemplate.getForObject(getUrl("/" + id ),ClientResponseDTO.class);
        } catch (HttpClientErrorException e){
            throw ClientException.CODE.NO_SUCH_CLIENT.get();
        }
    }


    private String getUrl(String action){
        return "http://Client-Service/client_service" + action;
    }
}
