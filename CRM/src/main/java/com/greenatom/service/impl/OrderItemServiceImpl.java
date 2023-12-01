package com.greenatom.service.impl;

import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.dto.orderitem.OrderItemResponseDTO;
import com.greenatom.domain.dto.orderitem.OrderItemSearchCriteria;
import com.greenatom.domain.mapper.OrderItemMapper;
import com.greenatom.exception.OrderItemException;
import com.greenatom.repository.OrderItemRepository;
import com.greenatom.repository.criteria.OrderItemCriteriaRepository;
import com.greenatom.service.OrderItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CartProductServiceImpl является сервисом для работы со списком покупок. Он использует различные репозитории для
 * доступа к данным и преобразует их с помощью mapper в формат DTO (Data Transfer Object).
 * findAll() - метод для получения всех покупок из заказа. Он регистрирует сообщение в логе и передает список
 * продуктов в mapper для преобразования в формат DTO.
 *
 * <p>findOne() - метод для получения конкретного товара из заказа по его ID. Он также регистрирует сообщение
 * в логе и преобразует найденный товар в формат DTO с помощью mapper. В случае, если товар не найден,
 * выбрасывается исключение.
 * @author Максим Быков, Даниил Змаев
 * @version 1.0
 */

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemCriteriaRepository orderItemCriteriaRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<OrderItemResponseDTO> findAll(EntityPage entityPage, OrderItemSearchCriteria orderItemSearchCriteria) {
        return orderItemCriteriaRepository
                .findAllWithFilters(entityPage, orderItemSearchCriteria)
                .map(orderItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderItemResponseDTO findOne(Long id) {
        return orderItemMapper.toDto(orderItemRepository
                .findById(id)
                .orElseThrow(OrderItemException.CODE.NO_SUCH_ORDER::get));
    }

    @Override
    @Transactional
    public OrderItemResponseDTO updateCartProduct(OrderItemResponseDTO cartProduct) {
        return orderItemRepository
                .findById(cartProduct.getId())
                .map(existingEvent -> {
                    orderItemMapper.partialUpdate(existingEvent, cartProduct);

                    return existingEvent;
                })
                .map(orderItemRepository::save)
                .map(orderItemMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public void deleteCartProduct(Long id) {
        orderItemRepository.delete(orderItemRepository
                .findById(id)
                .orElseThrow(OrderItemException.CODE.NO_SUCH_ORDER_ITEM::get));
    }
}
