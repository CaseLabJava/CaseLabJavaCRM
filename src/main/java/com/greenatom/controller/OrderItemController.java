package com.greenatom.controller;

import com.greenatom.controller.api.OrderItemApi;
import com.greenatom.domain.dto.item.OrderItemDTO;
import com.greenatom.domain.dto.item.OrderItemRequest;
import com.greenatom.service.OrderItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * @autor Максим Быков
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/api/orderItem")
public class OrderItemController implements OrderItemApi {
    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping(value = "/get/{id}", produces = {"application/json"})
    public OrderItemDTO getOrderItem(@PathVariable Long id) {
        return orderItemService.findOne(id).orElseThrow(EntityNotFoundException::new);
    }


    @PutMapping(value = "/update", produces = {"application/json"})
    public OrderItemDTO updateOrderItem(@RequestBody OrderItemDTO cartProduct) {
        return orderItemService.updateCartProduct(cartProduct);
    }

    @PostMapping(value = "/add", produces = {"application/json"})
    public ResponseEntity<Void> addOrderItem(@RequestBody OrderItemRequest orderItem) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = "/delete/{id}",
            produces = {"application/json"})
    public void deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteCartProduct(id);
    }

}
