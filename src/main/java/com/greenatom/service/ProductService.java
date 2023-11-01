package com.greenatom.service;

import com.greenatom.domain.dto.product.ProductRequestDTO;
import com.greenatom.domain.dto.product.ProductResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductService {

    @Transactional(readOnly = true)
    List<ProductResponseDTO> findAll();

    @Transactional(readOnly = true)
    ProductResponseDTO findOne(Long id);

    @Transactional
    ProductResponseDTO save(ProductRequestDTO product);

    @Transactional
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO product);

    @Transactional
    void deleteProduct(Long id);
    @Transactional(readOnly = true)
    List<ProductResponseDTO> findAll(Integer pagePosition, Integer pageLength, String name, Integer cost);
}
