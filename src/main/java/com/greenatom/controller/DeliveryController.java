package com.greenatom.controller;

import com.greenatom.controller.api.DeliveryApi;
import com.greenatom.domain.dto.delivery.DeliveryResponseDTO;
import com.greenatom.domain.dto.delivery.DeliverySearchCriteria;
import com.greenatom.domain.dto.employee.EntityPage;
import com.greenatom.service.DeliveryService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping(value = "/api/deliveries")
public class DeliveryController implements DeliveryApi {
    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping(value = "/start-delivery", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_COURIER', 'ROLE_ADMIN')")
    public ResponseEntity<Void> changeToInProcess(@RequestParam Long deliveryId, @RequestParam Long employeeId) {
        deliveryService.changeStatusToInProgress(employeeId, deliveryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/finish-delivery", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_COURIER', 'ROLE_ADMIN')")
    public ResponseEntity<Void> changeToDone(@RequestParam Long deliveryId, @RequestParam Long employeeId) {
        deliveryService.changeStatusToDone(employeeId, deliveryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping()
    @PreAuthorize(value = "hasAnyRole('ROLE_COURIER', 'ROLE_ADMIN')")
    public ResponseEntity<List<DeliveryResponseDTO>> findAll
            (@RequestParam(defaultValue = "0") Integer pageNumber,
             @RequestParam(defaultValue = "10") Integer pageSize,
             @RequestParam(required = false) Long orderId,
             @RequestParam(required = false) Long courierId,
             @RequestParam(required = false) String deliveryStatus,
             @RequestParam(required = false)Instant startTime,
             @RequestParam(required = false) Instant endTime,
             @RequestParam(required = false, defaultValue = "id") String sortBy,
             @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection) {
        return ResponseEntity.status(HttpStatus.OK).body(deliveryService.findAll(
                new EntityPage(pageNumber,
                        pageSize,
                        sortDirection,
                        sortBy),
                new DeliverySearchCriteria(0L,
                        orderId,
                        courierId,
                        deliveryStatus,
                        startTime,
                        endTime)));
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole('ROLE_COURIER', 'ROLE_ADMIN')")
    public ResponseEntity<DeliveryResponseDTO> findOne(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(deliveryService.findById(id));
    }
}