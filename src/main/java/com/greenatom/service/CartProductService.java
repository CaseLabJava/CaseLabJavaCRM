package com.greenatom.service;

import com.greenatom.domain.dto.CartProductDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartProductService {

    @Transactional(readOnly = true)
    List<CartProductDTO> findAll();

    @Transactional(readOnly = true)
    Optional<CartProductDTO> findOne(Long id);

    @Transactional
    CartProductDTO save(CartProductDTO cartProduct);

    @Transactional
    CartProductDTO updateCartProduct(CartProductDTO cartProduct);

    @Transactional
    void deleteCartProduct(Long id);
}
