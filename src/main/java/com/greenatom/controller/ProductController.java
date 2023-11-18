package com.greenatom.controller;

import com.greenatom.controller.api.ProductApi;
import com.greenatom.domain.dto.employee.EntityPage;
import com.greenatom.domain.dto.product.ProductRequestDTO;
import com.greenatom.domain.dto.product.ProductResponseDTO;
import com.greenatom.domain.dto.product.ProductSearchCriteria;
import com.greenatom.service.ProductService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPERVISOR')")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findOne(id));
    }

    @GetMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPERVISOR')")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(@RequestParam(defaultValue = "0") Integer pagePosition,
                                                   @RequestParam(defaultValue = "10") Integer pageLength,
                                                   @RequestParam(required = false) String productName,
                                                   @RequestParam(required = false) String unit,
                                                   @RequestParam(required = false) Long storageAmount,
                                                   @RequestParam(required = false) Long cost,
                                                   @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                   @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findAll(
                        new EntityPage(pagePosition, pageLength, sortDirection, sortBy),
                        new ProductSearchCriteria(0L, productName, unit, storageAmount, cost)));
    }

    @PatchMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO product) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, product));
    }

    @PostMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductRequestDTO product) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.save(product));
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
