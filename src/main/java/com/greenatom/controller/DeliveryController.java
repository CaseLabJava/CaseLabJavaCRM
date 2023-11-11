package com.greenatom.controller;

import com.greenatom.controller.api.DeliveryApi;
import com.greenatom.service.DeliveryService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/deliveries")
public class DeliveryController implements DeliveryApi {
    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping(value = "/take-delivery", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_COUIRER', 'ROLE_ADMIN')")
    public ResponseEntity<Void> changeToInProcess(@Parameter Long courierId, @Parameter Long deliveryId) {
        deliveryService.changeStatusToInProgress(courierId, deliveryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/finish-delivery", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_COUIRER', 'ROLE_ADMIN')")
    public ResponseEntity<Void> changeToDone(@Parameter Long courierId, @Parameter Long deliveryId) {
        deliveryService.changeStatusToDone(courierId, deliveryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}