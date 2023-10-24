package com.greenatom.controller;

import com.greenatom.domain.dto.OrderItemDTO;
import com.greenatom.service.OrderItemService;
import jakarta.persistence.EntityNotFoundException;
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
public class OrderItemController {
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
    public OrderItemDTO addOrderItem(@RequestBody OrderItemDTO cartProduct) {
        return orderItemService.save(cartProduct);
    }

    @DeleteMapping(value = "/delete/{id}",
            produces = {"application/json"})
    public void deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteCartProduct(id);
    }

}
