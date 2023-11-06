package com.greenatom.service.impl;

import com.greenatom.domain.dto.item.OrderItemResponseDTO;
import com.greenatom.domain.mapper.OrderItemMapper;
import com.greenatom.repository.OrderItemRepository;
import com.greenatom.service.OrderItemService;
import com.greenatom.utils.exception.OrderItemException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional(readOnly = true)
    public List<OrderItemResponseDTO> findAll() {
        return orderItemMapper.toDto(orderItemRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderItemResponseDTO findOne(Long id) {
        return orderItemMapper.toDto(orderItemRepository
                .findById(id)
                .orElseThrow(OrderItemException.CODE.NO_SUCH_ORDER::get));
    }

    @Override
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
    public void deleteCartProduct(Long id) {
        orderItemRepository.delete(orderItemRepository
                .findById(id)
                .orElseThrow(OrderItemException.CODE.NO_SUCH_ORDER_ITEM::get));
    }
}
