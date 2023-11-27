package com.greenatom.controller;

import com.greenatom.controller.api.PreparingOrderApi;
import com.greenatom.domain.dto.employee.EntityPage;
import com.greenatom.domain.dto.preparing_order.PreparingOrderResponseDTO;
import com.greenatom.domain.dto.preparing_order.PreparingOrderSearchCriteria;
import com.greenatom.service.impl.PreparingOrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping(value = "/api/preparing-orders")
@RequiredArgsConstructor
@CrossOrigin
public class PreparingOrderController implements PreparingOrderApi {
    private final PreparingOrderServiceImpl preparingOrderService;

    @GetMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_WAREHOUSE_WORKER')")
    @Override
    public ResponseEntity<List<PreparingOrderResponseDTO>> getPreparingOrders(@RequestParam(defaultValue = "0") Integer pageNumber,
                                                                              @RequestParam(defaultValue = "10") Integer pageSize,
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
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_WAREHOUSE_WORKER')")
    @Override
    public ResponseEntity<PreparingOrderResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(preparingOrderService.findOne(id));
    }

    @PostMapping("/appoint-collector")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_WAREHOUSE_WORKER')")
    @Override
    public ResponseEntity<Void> appointCollector(@RequestParam(required = false) Long employeeId,
                                                 @RequestParam Long preparingOrderId) {
        preparingOrderService.appointCollector(employeeId, preparingOrderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/finish-preparing-order")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_WAREHOUSE_WORKER')")
    public ResponseEntity<Void> finishPreparingOrder(@RequestParam Long employeeId,
                                                     @RequestParam Long preparingOrderId) {
        preparingOrderService.finishPreparingOrder(employeeId, preparingOrderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
