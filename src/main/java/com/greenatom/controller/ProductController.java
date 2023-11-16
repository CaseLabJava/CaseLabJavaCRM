package com.greenatom.controller;

import com.greenatom.controller.api.ProductApi;
import com.greenatom.domain.dto.product.ProductRequestDTO;
import com.greenatom.domain.dto.product.ProductResponseDTO;
import com.greenatom.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Этот код является контроллером для API управления продуктами. Он предоставляет методы GET, PUT и POST для
 * выполнения следующих операций с продуктами:
 *
 * <p>– GET /get/{id} возвращает информацию о продукте с заданным ID.
 * <p>– GET /get возвращает информацию о продуктах с фильтрацией по названию и стоимости.
 * <p>– PUT /update обновляет информацию о продукте, используя данные из тела запроса.
 * <p>– POST /add добавляет новый продукт, используя данные из тела запроса.
 * <p>- DELETE /delete/{id} удаляет продукт с заданным идентификатором.
 *
 * @author Максим Быков, Степан Моргачев
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/api/products")
public class ProductController implements ProductApi {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/get/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPERVISOR')")
    public ProductResponseDTO getProduct(@PathVariable Long id) {
        return productService.findOne(id);
    }

    @GetMapping(value = "/get", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPERVISOR')")
    public List<ProductResponseDTO> getAllProducts(@RequestParam(defaultValue = "0") Integer pagePosition,
                                                   @RequestParam(defaultValue = "20") Integer pageLength,
                                                   @RequestParam(defaultValue = "", required = false) String name,
                                                   @RequestParam(defaultValue = "0x7fffffff", required = false) Integer cost) {
        return productService.findAll(pagePosition, pageLength, name, cost);
    }

    @PatchMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public ProductResponseDTO updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO product) {
        return productService.updateProduct(id, product);
    }

    @PostMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public ProductResponseDTO addProduct(@RequestBody ProductRequestDTO product) {
        return productService.save(product);
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

}
