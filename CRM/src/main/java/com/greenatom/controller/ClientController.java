package com.greenatom.controller;

import com.greenatom.controller.api.ClientApi;
import com.greenatom.domain.dto.client.ClientRequestDTO;
import com.greenatom.domain.dto.client.ClientResponseDTO;
import com.greenatom.domain.dto.client.ClientSearchCriteria;
import com.greenatom.domain.dto.EntityPage;
import com.greenatom.service.ClientService;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

public class ClientController implements ClientApi {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPERVISOR', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ClientResponseDTO> findOne(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientService.findOne(id));
    }

    @GetMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<Page<ClientResponseDTO>> findAll(
            @RequestParam(defaultValue = "0")
            @Min(value = 0) Integer pagePosition,
            @RequestParam(defaultValue = "10")
            @Min(value = 1)
            Integer pageSize,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
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
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientService.findAll(
                        new EntityPage(pagePosition, pageSize, sortDirection, sortBy),
                        new ClientSearchCriteria(
                                0L, firstname,
                                lastname,
                                patronymic,
                                company,
                                bank,
                                inn,
                                ogrn,
                                correspondentAccount,
                                address,
                                email,
                                phoneNumber)));
    }

    @PatchMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id, @RequestBody ClientRequestDTO client) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientService.updateClient(id, client));
    }

    @PostMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ClientResponseDTO> addClient(@RequestBody ClientRequestDTO client) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientService.save(client));
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
