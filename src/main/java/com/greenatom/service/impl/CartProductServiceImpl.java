package com.greenatom.service.impl;

import com.greenatom.domain.dto.CartProductDTO;
import com.greenatom.domain.entity.CartProduct;
import com.greenatom.domain.mapper.CartProductMapper;
import com.greenatom.repository.CartProductRepository;
import com.greenatom.repository.ProductRepository;
import com.greenatom.repository.RequestRepository;
import com.greenatom.service.CartProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartProductServiceImpl implements CartProductService {
    private final Logger log = LoggerFactory.getLogger(CartProductService.class);
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;
    private final CartProductMapper cartProductMapper;
    private final RequestRepository requestRepository;

    @Override
    public List<CartProductDTO> findAll() {
        log.debug("Request to get all CartProducts");
        return cartProductMapper.toDto(cartProductRepository.findAll());
    }

    @Override
    public Optional<CartProductDTO> findOne(Long id) {
        log.debug("Request to get CartProduct : {}", id);
        return Optional.ofNullable(cartProductMapper.toDto(cartProductRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Request not found with id: " + id))));
    }

    @Override
    public CartProductDTO save(CartProductDTO cartProductDTO) {
        log.debug("Request to save cartProduct : {}", cartProductDTO);
        CartProduct cartProduct = cartProductMapper.toEntity(cartProductDTO);
        cartProduct.setProduct(productRepository.findById(cartProductDTO.getProductId()).orElseThrow());
        cartProduct.setRequest(requestRepository.findById(cartProductDTO.getRequestId()).orElseThrow());
        cartProductRepository.save(cartProduct);
        return cartProductMapper.toDto(cartProduct);
    }

    @Override
    public CartProductDTO updateCartProduct(CartProductDTO cartProduct) {
        log.debug("Request to partially update CartProduct : {}", cartProduct);
        return cartProductRepository
                .findById(cartProduct.getId())
                .map(existingEvent -> {
                    cartProductMapper.partialUpdate(existingEvent, cartProduct);

                    return existingEvent;
                })
                .map(cartProductRepository::save)
                .map(cartProductMapper::toDto).orElseThrow(
                        EntityNotFoundException::new);
    }

    @Override
    public void deleteCartProduct(Long id) {
        cartProductRepository
                .findById(id)
                .ifPresent(cartProduct -> {
                    cartProductRepository.delete(cartProduct);
                    log.debug("Deleted CartProduct: {}", cartProduct);
                });
    }}