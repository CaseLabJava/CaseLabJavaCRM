package com.greenatom.controller;

import com.greenatom.controller.api.OrderItemApi;
import com.greenatom.domain.dto.item.OrderItemResponseDTO;
import com.greenatom.service.OrderItemService;
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

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public OrderItemResponseDTO getOrderItem(@PathVariable Long id) {
        return orderItemService.findOne(id);
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    public void deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteCartProduct(id);
    }
}