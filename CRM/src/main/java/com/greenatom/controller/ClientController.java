package com.greenatom.controller;

import com.greenatom.controller.api.ClientApi;
import com.greenatom.domain.dto.client.ClientRegistrationDTO;
import com.greenatom.domain.dto.client.ClientRequestDTO;
import com.greenatom.domain.dto.client.ClientResponseDTO;
import com.greenatom.restTemplate.ClientRestTemplate;
import com.greenatom.utils.url.GenerateUrl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Этот код - контроллер, обрабатывающий запросы API для управления клиентами. Он предоставляет GET и PUT методы
 * для выполнения следующих операций:
 *
 * <p>– GET /get/{id} - возвращает информацию о клиенте с указанным идентификатором
 * <p>– PUT /update - обновляет информацию о клиенте, переданную в запросе тела
 * <p>– POST /add - добавляет нового клиента, используя данные из запроса тела
 *
 * <p>Эти операции выполняются при помощи сервиса ClientService, реализующего бизнес-логику.
 *
 * @author Максим Быков
 * @version 1.0
 */

@RestController
@RequestMapping(value = "/api/clients")
@RequiredArgsConstructor
public class ClientController implements ClientApi {

    private final ClientRestTemplate clientRestTemplate;


    @GetMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPERVISOR', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ClientResponseDTO> findOne(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientRestTemplate.getOneById(id));
    }

    @GetMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<Page<ClientResponseDTO>> findAll(
            @RequestParam(defaultValue = "0")
            @Min(value = 0) Integer pagePosition,
            @RequestParam(defaultValue = "10")
            @Min(value = 1)
            Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String patronymic,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String bank,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String correspondentAccount,
            @RequestParam(required = false) String inn,
            @RequestParam(required = false) String ogrn,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String email,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://Client-Service/client_service")
                .queryParam("pagePosition",pagePosition)
                .queryParam("pageSize", pageSize)
                .queryParam("sortBy", sortBy)
                .queryParam("sortDirection", sortDirection);

        Map<String, String> params = new HashMap<>();
        params.put("firstName", name);
        params.put("lastname", surname);
        params.put("patronymic", patronymic);
        params.put("address", address);
        params.put("bank", bank);
        params.put("company", company);
        params.put("correspondentAccount", correspondentAccount);
        params.put("inn", inn);
        params.put("ogrn", ogrn);
        params.put("phoneNumber", phoneNumber);
        params.put("email", email);


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientRestTemplate.findAll(GenerateUrl.generateUrl(params, builder)));
    }

    @PatchMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id,
                                                          @RequestBody @Valid ClientRequestDTO client) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientRestTemplate.update(client, id));
    }

    @PostMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ClientResponseDTO> addClient(@RequestBody ClientRegistrationDTO client) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientRestTemplate.save(client));
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientRestTemplate.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
