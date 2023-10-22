package com.greenatom.controller;

import com.greenatom.domain.dto.CartProductDTO;
import com.greenatom.service.CartProductService;
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
@RequestMapping(value = "/api/cartProduct")
public class CartProductController {
    private final CartProductService cartProductService;

    public CartProductController(CartProductService CartProductService) {
        this.cartProductService = CartProductService;
    }

    @GetMapping(value = "/get/{id}", produces = {"application/json"})
    public CartProductDTO getCartProduct(@PathVariable Long id) {
        return cartProductService.findOne(id).orElseThrow(EntityNotFoundException::new);
    }


    @PutMapping(value = "/update", produces = {"application/json"})
    public CartProductDTO updateCartProduct(@RequestBody CartProductDTO cartProduct) {
        return cartProductService.updateCartProduct(cartProduct);
    }

    @PostMapping(value = "/add", produces = {"application/json"})
    public CartProductDTO addCartProduct(@RequestBody CartProductDTO cartProduct) {
        return cartProductService.save(cartProduct);
    }

    @DeleteMapping(value = "/delete/{id}",
            produces = {"application/json"})
    public void deleteCartProduct(@PathVariable Long id) {
        cartProductService.deleteCartProduct(id);
    }

}
