package com.greenatom.controller;

import com.greenatom.domain.dto.EstimateDTO;
import com.greenatom.service.EstimateService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/estimate")
public class EstimateController {
    private final EstimateService estimateService;

    public EstimateController(EstimateService estimateService) {
        this.estimateService = estimateService;
    }

    @GetMapping(value = "/get/{id}", produces = {"application/json"})
    public EstimateDTO getEstimate(@PathVariable Long id) {
        return estimateService.findOne(id).orElseThrow(EntityNotFoundException::new);
    }


    @PutMapping(value = "/update", produces = {"application/json"})
    public EstimateDTO updateEstimate(@RequestBody EstimateDTO estimate) {
        return estimateService.updateEstimate(estimate);
    }

    @PostMapping(value = "/add", produces = {"application/json"})
    public EstimateDTO addEstimate(@RequestBody EstimateDTO estimate) {
        return estimateService.save(estimate);
    }

    @DeleteMapping(value = "/delete/{id}",
            produces = {"application/json"})
    public void deleteEstimate(@PathVariable Long id) {
        estimateService.deleteEstimate(id);
    }

}
