package com.greenatom.service.impl;

import com.greenatom.domain.dto.delivery.DeliveryResponseDTO;
import com.greenatom.domain.dto.delivery.DeliverySearchCriteria;
import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.entity.Courier;
import com.greenatom.domain.entity.Delivery;
import com.greenatom.domain.enums.DeliveryStatus;
import com.greenatom.domain.enums.OrderStatus;
import com.greenatom.domain.mapper.DeliveryMapper;
import com.greenatom.exception.CourierException;
import com.greenatom.exception.DeliveryException;
import com.greenatom.repository.CourierRepository;
import com.greenatom.repository.DeliveryRepository;
import com.greenatom.repository.criteria.DeliveryCriteriaRepository;
import com.greenatom.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final CourierRepository courierRepository;
    private final DeliveryMapper deliveryMapper;
    private final DeliveryCriteriaRepository deliveryCriteriaRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<DeliveryResponseDTO> findAll(EntityPage entityPage,
                                             DeliverySearchCriteria deliverySearchCriteria) {
        return deliveryCriteriaRepository.findAllWithFilters(entityPage,deliverySearchCriteria).map(deliveryMapper::toDto);
    }

    @Override
    @Transactional
    public DeliveryResponseDTO findById(Long id) {
        return deliveryMapper.toDto(deliveryRepository
                .findById(id)
                .orElseThrow(DeliveryException.CODE.NO_SUCH_DELIVERY::get));
    }

    @Override
    @Transactional
    public void changeStatusToInProgress(String username, Long deliveryId) {
        Courier courier = courierRepository
                .findCourierByEmployeeUsername(username)
                .orElseThrow(CourierException.CODE.NO_SUCH_COURIER::get);
        Delivery delivery = deliveryRepository
                .findById(deliveryId)
                .orElseThrow(DeliveryException.CODE.NO_SUCH_DELIVERY::get);
        if (!courier.getIsActive()) {
            throw DeliveryException.CODE.FORBIDDEN.get();
        }
        if (!Objects.equals(delivery.getDeliveryStatus(), DeliveryStatus.WAITING_FOR_DELIVERY)) {
            throw DeliveryException.CODE.INVALID_STATUS.get();
        }
        courier.setIsActive(false);
        delivery.setCourier(courier);
        delivery.setDeliveryStatus(DeliveryStatus.IN_PROCESS);
    }

    @Override
    @Transactional
    public void changeStatusToDone(String username, Long deliveryId) {
        Courier courier = courierRepository
                .findCourierByEmployeeUsername(username)
                .orElseThrow(CourierException.CODE.NO_SUCH_COURIER::get);
        Delivery delivery = deliveryRepository
                .findById(deliveryId)
                .orElseThrow(DeliveryException.CODE.NO_SUCH_DELIVERY::get);
        if (!Objects.equals(delivery.getCourier().getEmployee().getUsername(), username)) {
            throw DeliveryException.CODE.FORBIDDEN.get();
        }
        if (!Objects.equals(delivery.getDeliveryStatus(), DeliveryStatus.IN_PROCESS)) {
            throw DeliveryException.CODE.INVALID_STATUS.get();
        }
        delivery.setDeliveryStatus(DeliveryStatus.DONE);
        delivery.setEndTime(Instant.now());
        courier.setIsActive(true);
        delivery.getOrder().setOrderStatus(OrderStatus.DELIVERY_FINISHED);
    }
}
