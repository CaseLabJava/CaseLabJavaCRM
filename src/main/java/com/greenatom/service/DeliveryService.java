package com.greenatom.service;

import com.greenatom.domain.dto.delivery.DeliveryResponseDTO;
import com.greenatom.domain.enums.DeliveryStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DeliveryService {
    @Transactional(readOnly = true)
    List<DeliveryResponseDTO> findAll(Integer pagePosition, Integer pageLength, DeliveryStatus deliveryStatus);

    @Transactional
    void changeStatusToInProgress(Long courierId, Long deliveryId);

    @Transactional
    void changeStatusToDone(Long courierId, Long deliveryId);
}
