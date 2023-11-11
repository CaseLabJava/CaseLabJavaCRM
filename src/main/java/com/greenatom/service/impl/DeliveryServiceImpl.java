package com.greenatom.service.impl;

import com.greenatom.domain.dto.delivery.DeliveryResponseDTO;
import com.greenatom.domain.entity.Courier;
import com.greenatom.domain.entity.Delivery;
import com.greenatom.domain.enums.DeliveryStatus;
import com.greenatom.domain.enums.OrderStatus;
import com.greenatom.domain.mapper.DeliveryMapper;
import com.greenatom.exception.CourierException;
import com.greenatom.exception.DeliveryException;
import com.greenatom.repository.CourierRepository;
import com.greenatom.repository.DeliveryRepository;
import com.greenatom.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final CourierRepository courierRepository;
    private final DeliveryMapper deliveryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryResponseDTO> findAll(Integer pagePosition, Integer pageLength,
                                             DeliveryStatus deliveryStatus) {
        return deliveryMapper.toDto(deliveryRepository.findAllByDeliveryStatus(deliveryStatus,
                PageRequest.of(pagePosition, pageLength)));
    }

    @Override
    @Transactional
    public void changeStatusToInProgress(Long courierId, Long deliveryId) {
        Delivery delivery = deliveryRepository
                .findById(deliveryId)
                .orElseThrow(DeliveryException.CODE.NO_SUCH_DELIVERY::get);

        if (Objects.equals(delivery.getDeliveryStatus(), DeliveryStatus.WAITING_FOR_DELIVERY)) {
            delivery.setCourier(courierRepository
                    .findById(courierId)
                    .orElseThrow(DeliveryException.CODE.NO_SUCH_COURIER::get));
            delivery.setDeliveryStatus(DeliveryStatus.IN_PROCESS);
        } else {
            throw DeliveryException.CODE.INVALID_STATUS.get();
        }
        deliveryRepository.save(delivery);
    }

    @Override
    @Transactional
    public void changeStatusToDone(Long courierId, Long deliveryId) {
        Courier courier = courierRepository
                .findById(courierId)
                .orElseThrow(CourierException.CODE.NO_SUCH_COURIER::get);
        Delivery delivery = deliveryRepository
                .findById(deliveryId)
                .orElseThrow(DeliveryException.CODE.NO_SUCH_DELIVERY::get);
        if (!delivery.getCourier().equals(courier)) {
            throw DeliveryException.CODE.FORBIDDEN.get();
        }
        if (Objects.equals(delivery.getDeliveryStatus(), DeliveryStatus.IN_PROCESS)) {
            delivery.setDeliveryStatus(DeliveryStatus.DONE);
            courier.setIsActive(false);
        } else {
            throw DeliveryException.CODE.INVALID_STATUS.get();
        }
        courierRepository.save(courier);
        deliveryRepository.save(delivery);
        delivery.getOrder().setOrderStatus(OrderStatus.DELIVERY_FINISHED);
    }
}
