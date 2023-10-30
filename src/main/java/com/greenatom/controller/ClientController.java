package com.greenatom.controller;

import com.greenatom.controller.api.ClientAPI;
import com.greenatom.domain.dto.ClientDTO;
import com.greenatom.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Этот код - контроллер, обрабатывающий запросы API для управления клиентами. Он предоставляет GET и PUT методы
 * для выполнения следующих операций:
 *
 * <p>– GET /get/{id} - возвращает информацию о клиенте с указанным идентификатором
 * <p>– PUT /update - обновляет информацию о клиенте, переданную в запросе тела
 * <p>– POST /add - добавляет нового клиента, используя данные из запроса тела
 *
 * <p>Эти операции выполняются при помощи сервиса ClientService, реализующего бизнес-логику.
 * @author Максим Быков
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/api/clients")
public class ClientController implements ClientAPI {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
        Optional<ClientDTO> clientById = clientService.findOne(id);
        return clientById.map(ResponseEntity::ok).orElseGet(
                () -> ResponseEntity.notFound().build());
    }

    @GetMapping (produces = {"application/json"})
    public ResponseEntity<List<ClientDTO>> getClientsResponse(@RequestParam(defaultValue = "0") Integer pageNumber,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              @RequestParam(defaultValue = "", required = false) String company,
                                              @RequestParam(defaultValue = "", required = false) String firstname,
                                              @RequestParam(defaultValue = "", required = false) String lastname,
                                              @RequestParam(defaultValue = "", required = false) String patronymic
                                              ) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.findClientPageByParams(pageNumber,
                pageSize,
                company,
                firstname,
                lastname,
                patronymic));
    }


    @PutMapping(produces = {"application/json"})
    public ResponseEntity<ClientDTO> updateClient(@RequestBody ClientDTO client) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.updateClient(client));
    }

    @PostMapping(produces = {"application/json"})
    public ResponseEntity<ClientDTO> addClient(@RequestBody ClientDTO client) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.save(client));
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
