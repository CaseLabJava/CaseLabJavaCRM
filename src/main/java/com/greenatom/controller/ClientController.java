package com.greenatom.controller;

import com.greenatom.domain.dto.ClientDTO;
import com.greenatom.service.ClientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/client")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/get/{id}", produces = {"application/json"})
    public ClientDTO getClient(@PathVariable Long id) {
        return clientService.findOne(id).orElseThrow(EntityNotFoundException::new);
    }


    @PutMapping(value = "/update", produces = {"application/json"})
    public ClientDTO updateClient(@RequestBody ClientDTO client) {
        return clientService.updateClient(client);
    }

    @PostMapping(value = "/add", produces = {"application/json"})
    public ClientDTO addClient(@RequestBody ClientDTO client) {
        return clientService.save(client);
    }

    @DeleteMapping(value = "/delete/{id}",
            produces = {"application/json"})
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }

}
