package com.greenatom.service;

import com.greenatom.domain.dto.delivery.DeliveryResponseDTO;

import java.util.List;

public interface DeliveryService {

    DeliveryResponseDTO findById(Long id);
    List<DeliveryResponseDTO> findAll(Integer pagePosition, Integer pageLength, String deliveryStatus);

    void changeStatusToInProgress(Long courierId, Long deliveryId);

    void changeStatusToDone(Long courierId, Long deliveryId);
}
