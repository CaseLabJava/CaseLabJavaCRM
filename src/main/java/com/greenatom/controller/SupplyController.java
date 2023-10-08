package com.greenatom.controller;

import com.greenatom.domain.dto.SupplyDTO;
import com.greenatom.service.SupplyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/supply")
public class SupplyController {
    private final SupplyService supplyService;

    public SupplyController(SupplyService SupplyService) {
        this.supplyService = SupplyService;
    }

    @GetMapping(value = "/get/{id}", produces = {"application/json"})
    public SupplyDTO getSupply(@PathVariable Long id) {
        return supplyService.findOne(id).orElseThrow(EntityNotFoundException::new);
    }


    @PutMapping(value = "/update", produces = {"application/json"})
    public SupplyDTO updateSupply(@RequestBody SupplyDTO supply) {
        return supplyService.updateSupply(supply);
    }

    @PostMapping(value = "/add", produces = {"application/json"})
    public SupplyDTO addSupply(@RequestBody SupplyDTO supply) {
        return supplyService.save(supply);
    }

    @DeleteMapping(value = "/delete/{id}",
            produces = {"application/json"})
    public void deleteSupply(@PathVariable Long id) {
        supplyService.deleteSupply(id);
    }

}
