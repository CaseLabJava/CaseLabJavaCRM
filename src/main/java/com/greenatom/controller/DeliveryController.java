package com.greenatom.controller;

import com.greenatom.controller.api.DeliveryApi;
import com.greenatom.service.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/deliveries")
public class DeliveryController implements DeliveryApi {
    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }


    @PostMapping(value = "/progress/delivery/{deliveryId}/courier/{courierId}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_COUIRER', 'ROLE_ADMIN')")
    public ResponseEntity<Void> changeToInProcess(@PathVariable Long courierId, @PathVariable Long deliveryId) {
        deliveryService.changeStatusToInProgress(courierId, deliveryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/done/delivery/{deliveryId}/courier/{courierId}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_COUIRER', 'ROLE_ADMIN')")
    public ResponseEntity<Void> changeToDone(@PathVariable Long courierId, @PathVariable Long deliveryId) {
        deliveryService.changeStatusToDone(courierId, deliveryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
