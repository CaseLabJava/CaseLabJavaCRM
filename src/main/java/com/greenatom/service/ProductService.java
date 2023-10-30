package com.greenatom.service;

import com.greenatom.domain.dto.ProductDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductService {

    @Transactional(readOnly = true)
    List<ProductDTO> findAll();

    @Transactional(readOnly = true)
    ProductDTO findOne(Long id);

    @Transactional
    ProductDTO save(ProductDTO product);

    @Transactional
    ProductDTO updateProduct(ProductDTO product);

    @Transactional
    void deleteProduct(Long id);
    @Transactional(readOnly = true)
    List<ProductDTO> findAll(Integer pagePosition, Integer pageLength, String name, Integer cost);
}
