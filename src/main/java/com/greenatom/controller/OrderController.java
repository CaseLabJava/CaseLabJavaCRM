package com.greenatom.controller;

import com.greenatom.config.swagger.annotation.AccessDeniedResponse;
import com.greenatom.controller.api.OrderApi;
import com.greenatom.domain.dto.order.GenerateOrderRequest;
import com.greenatom.domain.dto.order.OrderDTO;
import com.greenatom.domain.dto.order.OrderRequest;
import com.greenatom.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
 * @author Максим Быков
 * @version 1.0
 */
@RestController
@AccessDeniedResponse
@RequestMapping(value = "/api/orders")
public class OrderController implements OrderApi {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<OrderDTO>> getOrders(@RequestParam(required = false, defaultValue = "0") Integer limit,
                                                    @RequestParam(required = false, defaultValue = "10") Integer offset,
                                                    @RequestParam(required = false, defaultValue = "orderDate") String sortField,
                                                    @RequestParam(required = false, defaultValue = "asc") String sortOrder,
                                                    @RequestParam(required = false) String orderStatus,
                                                    @RequestParam(required = false) String linkToFolder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService
                        .findByPaginationAndFilters(PageRequest.of(limit, offset, sort), orderStatus, linkToFolder));
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public OrderDTO getOrder(@PathVariable Long id) {
        return orderService.findOne(id);
    }

    @GetMapping(value = "/employee/{id}", produces = {"application/json"})
    public List<OrderDTO> getAllOrders(@Param("position") Integer pagePosition,
                                       @Param("length") Integer pageLength,
                                       @PathVariable("id") Long id) {
        return orderService.findAll(pagePosition, pageLength, id);
    }

    @PostMapping(value = "/draft")
    public OrderDTO addDraftOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createDraft(orderRequest);
    }

    @PostMapping(value = "/generate", produces = {"application/json"})
    public void generateOrder(@RequestBody GenerateOrderRequest request) {
        orderService.generateOrder(request);
    }

    @PostMapping(value = "/finish-order/{id}", produces = {"application/json"})
    public OrderDTO finishOrder(@PathVariable Long id) {
        return orderService.finishOrder(id);
    }

    @DeleteMapping(value = "/{id}/empty",
            produces = {"application/json"})
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}