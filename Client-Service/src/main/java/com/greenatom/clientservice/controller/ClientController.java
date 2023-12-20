package com.greenatom.clientservice.controller;

import com.greenatom.clientservice.domain.dto.client.*;
import com.greenatom.clientservice.domain.entity.Client;
import com.greenatom.clientservice.service.impl.ClientService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/client_service")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/email")
    public ResponseEntity<Optional<Client>> getOneByEmail(@RequestParam @Email String email){
        return ResponseEntity.ok(clientService.findOneByEmail(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getOneById(@PathVariable Long id){
        return ResponseEntity.ok(clientService.findOneById(id));
    }

    @PostMapping(value = "/add", produces = {"application/json"})
    public ResponseEntity<ClientResponseDTO> save(@RequestBody ClientCreateDTO clientCreateDTO){
        return ResponseEntity.ok(clientService.save(clientCreateDTO));
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id, @RequestBody ClientRequestDTO client){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientService.updateClient(id, client));
    }

    @GetMapping(produces = {"application/json"})
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
}
