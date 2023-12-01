package com.greenatom.controller;

import com.greenatom.controller.api.PreparingOrderApi;
import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.dto.preparing_order.PreparingOrderResponseDTO;
import com.greenatom.domain.dto.preparing_order.PreparingOrderSearchCriteria;
import com.greenatom.service.impl.PreparingOrderServiceImpl;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;

@RestController
@RequestMapping(value = "/api/preparing-orders")
@RequiredArgsConstructor
public class PreparingOrderController implements PreparingOrderApi {
    private final PreparingOrderServiceImpl preparingOrderService;

    @GetMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_WAREHOUSE_WORKER', 'ROLE_SUPER_ADMIN')")
    @Override
    public ResponseEntity<Page<PreparingOrderResponseDTO>> getAllPreparingOrders(
            @RequestParam(defaultValue = "0")
            @Min(value = 0)
            Integer pageNumber,
            @RequestParam(defaultValue = "10")
            @Min(value = 1)
            Integer pageSize,
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) String preparingOrderStatus,
            @RequestParam(required = false) Instant startTime,
            @RequestParam(required = false) Instant endTime,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection) {
        return ResponseEntity.ok(
                preparingOrderService.findAll(
                        new EntityPage(pageNumber,
                        pageSize,
                        sortDirection,
                        sortBy),
                        new PreparingOrderSearchCriteria(
                                orderId,
                                employeeId,
                                preparingOrderStatus,
                                startTime,
                                endTime)));
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_WAREHOUSE_WORKER', 'ROLE_SUPER_ADMIN')")
    @Override
    public ResponseEntity<PreparingOrderResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(preparingOrderService.findOne(id));
    }

    @PostMapping("/appoint-collector")
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_WAREHOUSE_WORKER', 'ROLE_SUPER_ADMIN')")
    @Override
    public ResponseEntity<Void> appointCollector(Principal principal,
                                                 @RequestParam Long preparingOrderId) {
        preparingOrderService.appointCollector(principal.getName(), preparingOrderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/finish-preparing-order")
    @PreAuthorize(value = "hasAnyRole('ROLE_WAREHOUSE_WORKER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<Void> finishPreparingOrder(Principal principal,
                                                     @RequestParam Long preparingOrderId) {
        preparingOrderService.finishPreparingOrder(principal.getName(), preparingOrderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
