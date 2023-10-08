package com.greenatom.controller;

import com.greenatom.domain.dto.RequestDTO;
import com.greenatom.service.RequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/request")
public class RequestController {
    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping(value = "/get/{id}", produces = {"application/json"})
    public RequestDTO getRequest(@PathVariable Long id) {
        return requestService.findOne(id).orElseThrow(EntityNotFoundException::new);
    }


    @PutMapping(value = "/update", produces = {"application/json"})
    public RequestDTO updateRequest(@RequestBody RequestDTO request) {
        return requestService.updateRequest(request);
    }

    @PostMapping(value = "/add", produces = {"application/json"})
    public RequestDTO addRequest(@RequestBody RequestDTO request) {
        return requestService.save(request);
    }

    @DeleteMapping(value = "/delete/{id}",
            produces = {"application/json"})
    public void deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);
    }

}
