package com.greenatom.controller;

import com.greenatom.domain.dto.ProductDTO;
import com.greenatom.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * Этот код является контроллером для API управления продуктами. Он предоставляет методы GET, PUT и POST для
 * выполнения следующих операций с продуктами:
 *
 * <p>– GET /get/{id} возвращает информацию о продукте с заданным ID.
 * <p>– PUT /update обновляет информацию о продукте, используя данные из тела запроса.
 * <p>– POST /add добавляет новый продукт, используя данные из тела запроса.
 *
 * @autor Максим Быков
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/get/{id}", produces = {"application/json"})
    public ProductDTO getProduct(@PathVariable Long id) {
        return productService.findOne(id).orElseThrow(EntityNotFoundException::new);
    }


    @PutMapping(value = "/update", produces = {"application/json"})
    public ProductDTO updateProduct(@RequestBody ProductDTO product) {
        return productService.updateProduct(product);
    }

    @PostMapping(value = "/add", produces = {"application/json"})
    public ProductDTO addProduct(@RequestBody ProductDTO product) {
        return productService.save(product);
    }

    @DeleteMapping(value = "/delete/{id}",
            produces = {"application/json"})
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

}
