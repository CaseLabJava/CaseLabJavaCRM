package com.greenatom.service.impl;

import com.greenatom.domain.dto.OrderItemDTO;
import com.greenatom.domain.entity.Order;
import com.greenatom.domain.entity.OrderItem;
import com.greenatom.domain.mapper.OrderItemMapper;
import com.greenatom.repository.OrderItemRepository;
import com.greenatom.repository.ProductRepository;
import com.greenatom.repository.OrderRepository;
import com.greenatom.service.OrderItemService;
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
public class OrderItemServiceImpl implements OrderItemService {
    private final Logger log = LoggerFactory.getLogger(OrderItemService.class);
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final OrderItemMapper cartProductMapper;
    private final OrderRepository orderRepository;

    @Override
    public List<OrderItemDTO> findAll() {
        log.debug("Order to get all CartProducts");
        return cartProductMapper.toDto(orderItemRepository.findAll());
    }

    @Override
    public Optional<OrderItemDTO> findOne(Long id) {
        log.debug("Order to get CartProduct : {}", id);
        return Optional.ofNullable(cartProductMapper.toDto(orderItemRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Order not found with id: " + id))));
    }

    @Override
    public OrderItemDTO save(OrderItemDTO orderItemDTO) {
        log.debug("Order to save cartProduct : {}", orderItemDTO);
        OrderItem orderItem = cartProductMapper.toEntity(orderItemDTO);
        orderItem.setProduct(productRepository.findById(orderItemDTO.getProductId()).orElseThrow());
        Order order = orderRepository.findById(orderItemDTO.getOrderId()).get();
        orderItem.setOrder(orderRepository.findById(orderItemDTO.getOrderId()).orElseThrow());
        orderItemRepository.save(orderItem);
        return cartProductMapper.toDto(orderItem);
    }

    @Override
    public OrderItemDTO updateCartProduct(OrderItemDTO cartProduct) {
        log.debug("Order to partially update CartProduct : {}", cartProduct);
        return orderItemRepository
                .findById(cartProduct.getId())
                .map(existingEvent -> {
                    cartProductMapper.partialUpdate(existingEvent, cartProduct);

                    return existingEvent;
                })
                .map(orderItemRepository::save)
                .map(cartProductMapper::toDto).orElseThrow(
                        EntityNotFoundException::new);
    }

    @Override
    public void deleteCartProduct(Long id) {
        orderItemRepository
                .findById(id)
                .ifPresent(cartProduct -> {
                    orderItemRepository.delete(cartProduct);
                    log.debug("Deleted CartProduct: {}", cartProduct);
                });
    }}