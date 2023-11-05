package com.greenatom.controller;

import com.greenatom.config.swagger.annotation.AccessDeniedResponse;
import com.greenatom.controller.api.PreparingOrderApi;
import com.greenatom.domain.dto.preparing_order.PreparingOrderResponseDTO;
import com.greenatom.service.impl.PreparingOrderServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AccessDeniedResponse
@RequestMapping(value = "/api/preparing_orders")
@RequiredArgsConstructor
public class PreparingOrderController implements PreparingOrderApi {
    private final PreparingOrderServiceImpl preparingOrderService;

    @GetMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_WAREHOUSE_WORKER', 'ROLE_ADMIN')")
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
}
