package com.greenatom.service;

import com.greenatom.domain.dto.delivery.DeliveryResponseDTO;
import com.greenatom.domain.enums.DeliveryStatus;

import java.util.List;

public interface DeliveryService {

    List<DeliveryResponseDTO> findAll(Integer pagePosition, Integer pageLength, DeliveryStatus deliveryStatus);

    void changeStatusToInProgress(Long courierId, Long deliveryId);

    void changeStatusToDone(Long courierId, Long deliveryId);
}
