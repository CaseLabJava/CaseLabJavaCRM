package com.greenatom.controller;

import com.greenatom.domain.dto.SuccessfulWorkDTO;
import com.greenatom.service.SuccessfulWorkService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/successfulWork")
public class SuccessfulWorkController {
    private final SuccessfulWorkService successfulWorkService;

    public SuccessfulWorkController(SuccessfulWorkService successfulWorkService) {
        this.successfulWorkService = successfulWorkService;
    }

    @GetMapping(value = "/get/{id}", produces = {"application/json"})
    public SuccessfulWorkDTO getSuccessfulWork(@PathVariable Long id) {
        return successfulWorkService.findOne(id).orElseThrow(EntityNotFoundException::new);
    }


    @PutMapping(value = "/update", produces = {"application/json"})
    public SuccessfulWorkDTO updateSuccessfulWork(@RequestBody SuccessfulWorkDTO successfulWork) {
        return successfulWorkService.updateSuccessfulWork(successfulWork);
    }

    @PostMapping(value = "/add", produces = {"application/json"})
    public SuccessfulWorkDTO addSuccessfulWork(@RequestBody SuccessfulWorkDTO successfulWork) {
        return successfulWorkService.save(successfulWork);
    }

    @DeleteMapping(value = "/delete/{id}",
            produces = {"application/json"})
    public void deleteSuccessfulWork(@PathVariable Long id) {
        successfulWorkService.deleteSuccessfulWork(id);
    }

}
