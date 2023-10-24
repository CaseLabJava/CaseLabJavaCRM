package com.greenatom.service.impl;

import com.greenatom.domain.dto.CartProductDTO;
import com.greenatom.domain.entity.CartProduct;
import com.greenatom.domain.mapper.CartProductMapper;
import com.greenatom.repository.CartProductRepository;
import com.greenatom.repository.ProductRepository;
import com.greenatom.repository.OrderRepository;
import com.greenatom.service.CartProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * CartProductServiceImpl является сервисом для работы со списком покупок. Он использует различные репозитории для
 * доступа к данным и преобразует их с помощью mapper в формат DTO (Data Transfer Object).
 * findAll() - метод для получения всех покупок из заказа. Он регистрирует сообщение в логе и передает список
 * продуктов в mapper для преобразования в формат DTO.
 *
 * <p>findOne() - метод для получения конкретного товара из заказа по его ID. Он также регистрирует сообщение
 * в логе и преобразует найденный товар в формат DTO с помощью mapper. В случае, если товар не найден,
 * выбрасывается исключение.
 * @autor Максим Быков, Даниил Змаев
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CartProductServiceImpl implements CartProductService {
    private final Logger log = LoggerFactory.getLogger(CartProductService.class);
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;
    private final CartProductMapper cartProductMapper;
    private final OrderRepository orderRepository;

    @Override
    public List<CartProductDTO> findAll() {
        log.debug("Order to get all CartProducts");
        return cartProductMapper.toDto(cartProductRepository.findAll());
    }

    @Override
    public Optional<CartProductDTO> findOne(Long id) {
        log.debug("Order to get CartProduct : {}", id);
        return Optional.ofNullable(cartProductMapper.toDto(cartProductRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Order not found with id: " + id))));
    }

    @Override
    public CartProductDTO save(CartProductDTO cartProductDTO) {
        log.debug("Order to save cartProduct : {}", cartProductDTO);
        CartProduct cartProduct = cartProductMapper.toEntity(cartProductDTO);
        cartProduct.setProduct(productRepository.findById(cartProductDTO.getProductId()).orElseThrow());
        cartProduct.setOrder(orderRepository.findById(cartProductDTO.getRequestId()).orElseThrow());
        cartProductRepository.save(cartProduct);
        return cartProductMapper.toDto(cartProduct);
    }

    @Override
    public CartProductDTO updateCartProduct(CartProductDTO cartProduct) {
        log.debug("Order to partially update CartProduct : {}", cartProduct);
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