package com.greenatom.service.impl;

import com.greenatom.domain.dto.delivery.DeliveryResponseDTO;
import com.greenatom.domain.entity.Courier;
import com.greenatom.domain.entity.Delivery;
import com.greenatom.domain.enums.DeliveryStatus;
import com.greenatom.domain.mapper.DeliveryMapper;
import com.greenatom.repository.CourierRepository;
import com.greenatom.repository.DeliveryRepository;
import com.greenatom.service.DeliveryService;
import com.greenatom.utils.exception.CourierException;
import com.greenatom.utils.exception.DeliveryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
    public List<DeliveryResponseDTO> findAll(Integer pagePosition, Integer pageLength,
                                             DeliveryStatus deliveryStatus) {
        return deliveryMapper.toDto(deliveryRepository.findAllByDeliveryStatus(deliveryStatus,
                PageRequest.of(pagePosition, pageLength)));
    }

    @Override
    public void changeStatusToInProgress(Long courierId, Long deliveryId) {
        Courier courier = courierRepository
                .findById(courierId)
                .orElseThrow(CourierException.CODE.NO_SUCH_COURIER::get);
        Delivery delivery = deliveryRepository
                .findById(deliveryId)
                .orElseThrow(DeliveryException.CODE.NO_SUCH_DELIVERY::get);

        if (Objects.equals(delivery.getDeliveryStatus(), DeliveryStatus.WAITING_FOR_DELIVERY) &&
                (Objects.equals(delivery.getCourier().getId(), courierId))) {
            delivery.setDeliveryStatus(DeliveryStatus.IN_PROCESS);
        } else {
            throw DeliveryException.CODE.INVALID_STATUS.get();
        }
        deliveryRepository.save(delivery);
    }

    @Override
    public void changeStatusToDone(Long courierId, Long deliveryId) {
        Courier courier = courierRepository
                .findById(courierId)
                .orElseThrow(CourierException.CODE.NO_SUCH_COURIER::get);
        Delivery delivery = deliveryRepository
                .findById(deliveryId)
                .orElseThrow(DeliveryException.CODE.NO_SUCH_DELIVERY::get);

        if (Objects.equals(delivery.getDeliveryStatus(), DeliveryStatus.IN_PROCESS) &&
                (Objects.equals(delivery.getCourier().getId(), courierId))) {
            delivery.setDeliveryStatus(DeliveryStatus.DONE);
            courier.setIsActive(false);
        } else {
            throw DeliveryException.CODE.INVALID_STATUS.get();
        }

        courierRepository.save(courier);
        deliveryRepository.save(delivery);
    }
}
