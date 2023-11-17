package com.greenatom.controller;

import com.greenatom.controller.api.DeliveryApi;
import com.greenatom.domain.dto.delivery.DeliveryResponseDTO;
import com.greenatom.service.DeliveryService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/deliveries")
public class DeliveryController implements DeliveryApi {
    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping(value = "/take-delivery", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_COURIER', 'ROLE_ADMIN')")
    public ResponseEntity<Void> changeToInProcess(@RequestParam Long courierId, @RequestParam Long deliveryId) {
        deliveryService.changeStatusToInProgress(courierId, deliveryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/finish-delivery", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_COURIER', 'ROLE_ADMIN')")
    public ResponseEntity<Void> changeToDone(@RequestParam Long courierId, @RequestParam Long deliveryId) {
        deliveryService.changeStatusToDone(courierId, deliveryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping()
    @PreAuthorize(value = "hasAnyRole('ROLE_COURIER', 'ROLE_ADMIN')")
    public ResponseEntity<List<DeliveryResponseDTO>> findAll(@RequestParam(defaultValue = "0") Integer pageNumber,
                                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                                             @RequestParam(defaultValue = "DONE") String status) {
        return ResponseEntity.status(HttpStatus.OK).body(deliveryService.findAll(pageNumber, pageSize, status));
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole('ROLE_COURIER', 'ROLE_ADMIN')")
    public ResponseEntity<DeliveryResponseDTO> findOne(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(deliveryService.findById(id));
    }
}