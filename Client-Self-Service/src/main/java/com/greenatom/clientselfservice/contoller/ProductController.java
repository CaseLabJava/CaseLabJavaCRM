package com.greenatom.clientselfservice.contoller;

import com.greenatom.clientselfservice.domain.dto.product.ProductResponseDTO;
import com.greenatom.clientselfservice.service.impl.ProductService;
import com.greenatom.clientselfservice.utils.url.GenerateUrl;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("self-service/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(defaultValue = "0")
            @Min(value = 0)
            Integer pagePosition,
            @RequestParam(defaultValue = "10")
            @Min(value = 1)
            Integer pageLength,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String unit,
            @RequestParam(required = false) Long storageAmount,
            @RequestParam(required = false) Long cost,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(getUrl(""))
                .queryParam("pagePosition",pagePosition)
                .queryParam("pageLength", pageLength)
                .queryParam("sortBy", sortBy)
                .queryParam("sortDirection", sortDirection);

        Map<String, String> stringParams = new HashMap<>();
        stringParams.put("productName", productName);
        stringParams.put("unit", unit);

        Map<String,Long> longParams = new HashMap<>();
        longParams.put("storageAmount", storageAmount);
        longParams.put("cost", cost);

        GenerateUrl.generateUrl(stringParams,builder);
        GenerateUrl.generateUrl(longParams,builder);

        return ResponseEntity.ok(productService.getAll(builder));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getOne(id));
    }

    private String getUrl (String action){
        return "http://Crm-Service/api/products" + action;
    }
}
