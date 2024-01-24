package com.greenatom.restTemplate;

import com.greenatom.domain.dto.client.ClientRegistrationDTO;
import com.greenatom.domain.dto.client.ClientRequestDTO;
import com.greenatom.domain.dto.client.ClientResponseDTO;
import com.greenatom.domain.dto.page.CustomPage;
import com.greenatom.domain.entity.Client;
import com.greenatom.domain.enums.ClientSource;
import com.greenatom.exception.ClientException;
import com.greenatom.utils.mapper.TranslateRusToEng;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
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

        UriComponentsBuilder componentsBuilder = UriComponentsBuilder.fromUriString(getUrl(""))
                .queryParam("email",email);

        ParameterizedTypeReference<Optional<Client>> responseType = new ParameterizedTypeReference<>() {};
        return restTemplate.exchange(componentsBuilder.toUriString(), HttpMethod.GET,null,responseType).getBody();
    }

    public ClientResponseDTO save(ClientRegistrationDTO clientRegistrationDTO) {
        clientRegistrationDTO.setClientSource(ClientSource.CRM);
        clientRegistrationDTO.setPassword(generatePassword(clientRegistrationDTO));
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

    public void deleteById(Long id){
        restTemplate.delete(getUrl("/" + id));
    }

    public ClientResponseDTO update(ClientRequestDTO clientRequestDTO, Long id){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ClientRequestDTO> request = new HttpEntity<>(clientRequestDTO,headers);
        return restTemplate.patchForObject(getUrl("/" + id),request,ClientResponseDTO.class);
    }

    public Page<ClientResponseDTO> findAll(UriComponentsBuilder builder){

        CustomPage<ClientResponseDTO> page = restTemplate.exchange(
                        builder.toUriString(),
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<CustomPage<ClientResponseDTO>>() {
                        })
                        .getBody();
        System.out.println(page.getContent());
        return page;
    }


    private String getUrl(String action){
        return "http://Client-Service/client_service" + action;
    }

    private String generatePassword(ClientRegistrationDTO clientRegistrationDTO) {
        StringBuilder password = new StringBuilder();
        String firstname = TranslateRusToEng.translateFromRusToEng(clientRegistrationDTO.getFirstname());
        String surname = TranslateRusToEng.translateFromRusToEng(clientRegistrationDTO.getLastname());
        String patronymic = TranslateRusToEng.translateFromRusToEng(clientRegistrationDTO.getPatronymic());
        password.append(surname)
                .append(firstname.charAt(0))
                .append(patronymic.charAt(0));
        return password.toString();
    }
}
