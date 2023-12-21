package com.greenatom.clientselfservice.contoller;

import com.greenatom.clientselfservice.domain.dto.order.OrderRequestDTO;
import com.greenatom.clientselfservice.domain.dto.order.OrderResponseDTO;
import com.greenatom.clientselfservice.service.impl.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("self_service/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping(value = "/draft")
    public ResponseEntity<OrderResponseDTO> addDraftOrder(Principal principal, @RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.createDraft(principal.getName(), orderRequestDTO));
    }
}
