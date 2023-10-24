package com.greenatom.controller;

import com.greenatom.controller.api.OrderApi;
import com.greenatom.domain.dto.OrderDTO;
import com.greenatom.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * Этот код представляет собой контроллер API для управления заявками. Он предоставляет набор методов
 * для выполнения различных операций с заявками:
 *
 * <p>– GET /get/{id}: Получение информации о запросе с указанным ID.
 * <p>– PUT /update: Обновление информации о запросе, используя данные из тела запроса.
 * <p>– POST /add: Создание нового запроса, используя данные из тела запроса.
 *
 * <p>Все эти операции выполняются с использованием сервиса OrderService, который реализует бизнес-логику
 * управления заявками.
 * @autor Максим Быков
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/api/request")
public class OrderController implements OrderApi {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/get/{id}", produces = {"application/json"})
    public OrderDTO getOrder(@PathVariable Long id) {
        return orderService.findOne(id).orElseThrow(EntityNotFoundException::new);
    }


    @PutMapping(value = "/update", produces = {"application/json"})
    public OrderDTO updateOrder(@RequestBody OrderDTO request) {
        return orderService.updateOrder(request);
    }

    @PostMapping(value = "/add", produces = {"application/json"})
    public OrderDTO addOrder(@RequestBody OrderDTO request) {
        return orderService.save(request);
    }

    @DeleteMapping(value = "/delete/{id}",
            produces = {"application/json"})
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

}
