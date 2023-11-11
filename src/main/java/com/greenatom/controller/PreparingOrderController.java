package com.greenatom.controller;

import com.greenatom.controller.api.PreparingOrderApi;
import com.greenatom.domain.dto.preparing_order.PreparingOrderResponseDTO;
import com.greenatom.service.impl.PreparingOrderServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/preparing_orders")
@RequiredArgsConstructor
public class PreparingOrderController implements PreparingOrderApi {
    private final PreparingOrderServiceImpl preparingOrderService;

    @GetMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_WAREHOUSE_WORKER')")
    @Override
    public ResponseEntity<List<PreparingOrderResponseDTO>> getPreparingOrders(@RequestParam(defaultValue = "0") Integer pageNumber,
                                                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                                                              @Parameter(
                                                                                      in = ParameterIn.QUERY,
                                                                                      name = "status",
                                                                                      schema = @Schema(allowableValues = {"WAITING_FOR_PREPARING", "IN_PROCESS", "DONE"}))
                                                                              @RequestParam(name = "статус заказа", defaultValue = "WAITING_FOR_PREPARING", required = false) String status) {
        return ResponseEntity.ok(preparingOrderService.findPreparingOrdersPageByParams(pageNumber, pageSize, status));
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
