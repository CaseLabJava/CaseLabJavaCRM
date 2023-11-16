package com.greenatom.controller;

import com.greenatom.controller.api.ClientApi;
import com.greenatom.domain.dto.client.ClientRequestDTO;
import com.greenatom.domain.dto.client.ClientResponseDTO;
import com.greenatom.service.ClientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPERVISOR')")
    public ClientResponseDTO getClient(@PathVariable Long id) {
        return clientService.findOne(id);
    }

    @GetMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public List<ClientResponseDTO> getClientsResponse(@RequestParam(defaultValue = "0") Integer pageNumber,
                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                      @RequestParam(defaultValue = "", required = false) String company,
                                                      @RequestParam(defaultValue = "", required = false) String firstname,
                                                      @RequestParam(defaultValue = "", required = false) String lastname,
                                                      @RequestParam(defaultValue = "", required = false) String patronymic) {
        return clientService.findClientPageByParams(pageNumber,
                pageSize,
                company,
                firstname,
                lastname,
                patronymic);
    }

    @PatchMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public ClientResponseDTO updateClient(@PathVariable Long id, @RequestBody ClientRequestDTO client) {
        return clientService.updateClient(id, client);
    }

    @PostMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public ClientResponseDTO addClient(@RequestBody ClientRequestDTO client) {
        return clientService.save(client);
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }

}
