package com.greenatom.controller;

import com.greenatom.controller.api.OrderItemApi;
import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.dto.orderitem.OrderItemResponseDTO;
import com.greenatom.domain.dto.orderitem.OrderItemSearchCriteria;
import com.greenatom.service.OrderItemService;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Этот код является контроллером, который обрабатывает запросы к API, связанному с управлением продуктами в корзине.
 * Он предоставляет методы GET и PUT для выполнения следующих задач:
 *
 * <p>GET /get/{id} - Возвращает информацию о продукте в корзине с указанным идентификатором.
 * <p>PUT /update - Обновляет информацию о продукте в корзине, переданную в теле запроса.
 * <p>POST /add - Добавляет новый продукт в корзину.
 *
 * <p>Все эти операции выполняются с использованием сервиса CartProductService, который предоставляет реализацию
 * бизнес-логики.
 * @author Максим Быков
 * @version 1.0
 */

@RestController
@RequestMapping(value = "/api/order-items")
public class OrderItemController implements OrderItemApi {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<Page<OrderItemResponseDTO>> getAllOrderItems(
            @RequestParam(defaultValue = "0")
            @Min(value = 0)
            Integer pagePosition,
            @RequestParam(defaultValue = "10")
            @Min(value = 1)
            Integer pageLength,
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String unit,
            @RequestParam(required = false) Long storageAmount,
            @RequestParam(required = false) Long cost,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(orderItemService.findAll(
                new EntityPage(pagePosition, pageLength, sortDirection, sortBy),
                new OrderItemSearchCriteria(0L, orderId, productId, productName, unit, storageAmount, cost)));
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<OrderItemResponseDTO> getOrderItem(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderItemService.findOne(id));
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteCartProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}