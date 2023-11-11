package com.greenatom.service.impl;

import com.greenatom.domain.dto.product.ProductRequestDTO;
import com.greenatom.domain.dto.product.ProductResponseDTO;
import com.greenatom.domain.entity.Product;
import com.greenatom.domain.mapper.ProductMapper;
import com.greenatom.exception.ProductException;
import com.greenatom.repository.ProductRepository;
import com.greenatom.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ProductServiceImpl является сервисом для работы со складом. Он использует ProductRepository для доступа к базе
 * данных, преобразует продукты в формат DTO и возвращает список товаров или конкретный товар по его ID.
 * @author Максим Быков
 * @version 1.0
 */

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll() {
        return productMapper.toDto(productRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO findOne(Long id) {
        log.debug("Order to get Product : {}", id);
        return productMapper.toDto(productRepository
                .findById(id)
                .orElseThrow(ProductException.CODE.NO_SUCH_PRODUCT::get));
    }

    @Override
    @Transactional
    public ProductResponseDTO save(ProductRequestDTO productRequestDTO) {
        Product product = productMapper.toEntity(productRequestDTO);
        return productMapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO product) {
        return productRepository
                .findById(id)
                .map(existingEvent -> {
                    productMapper.partialUpdate(existingEvent, productMapper.toResponse(product));
                    return existingEvent;
                })
                .map(productMapper::toDto)
                .orElseThrow(ProductException.CODE.NO_SUCH_PRODUCT::get);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        productRepository
                .findById(id)
                .ifPresent(productRepository::delete);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll(Integer pagePosition, Integer pageLength, String name, Integer cost) {
        return productMapper.toDto(productRepository.findProductByProductNameContainingAndCostBefore(
                PageRequest.of(pagePosition, pageLength), name, cost).toList());
    }
}
