package com.greenatom.controller;

import com.greenatom.controller.api.RequestApi;
import com.greenatom.domain.dto.RequestDTO;
import com.greenatom.service.RequestService;
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
 * <p>Все эти операции выполняются с использованием сервиса RequestService, который реализует бизнес-логику
 * управления заявками.
 * @autor Максим Быков
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/api/request")
public class RequestController implements RequestApi {
    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping(value = "/get/{id}", produces = {"application/json"})
    public RequestDTO getRequest(@PathVariable Long id) {
        return requestService.findOne(id).orElseThrow(EntityNotFoundException::new);
    }


    @PutMapping(value = "/update", produces = {"application/json"})
    public RequestDTO updateRequest(@RequestBody RequestDTO request) {
        return requestService.updateRequest(request);
    }

    @PostMapping(value = "/add", produces = {"application/json"})
    public RequestDTO addRequest(@RequestBody RequestDTO request) {
        return requestService.save(request);
    }

    @DeleteMapping(value = "/delete/{id}",
            produces = {"application/json"})
    public void deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);
    }

}
