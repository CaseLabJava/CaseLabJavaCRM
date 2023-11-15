package com.greenatom.service;

import com.greenatom.domain.dto.product.ProductRequestDTO;
import com.greenatom.domain.dto.product.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    List<ProductResponseDTO> findAll();

    ProductResponseDTO findOne(Long id);

    ProductResponseDTO save(ProductRequestDTO product);

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO product);

    void deleteProduct(Long id);
    List<ProductResponseDTO> findAll(Integer pagePosition, Integer pageLength, String name, Integer cost);
}
