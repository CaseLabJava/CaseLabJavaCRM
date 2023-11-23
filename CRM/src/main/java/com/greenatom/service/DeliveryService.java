package com.greenatom.service;

import com.greenatom.domain.dto.delivery.DeliveryResponseDTO;
import com.greenatom.domain.dto.delivery.DeliverySearchCriteria;
import com.greenatom.domain.dto.employee.EntityPage;

import java.util.List;

public interface DeliveryService {

    List<DeliveryResponseDTO> findAll(EntityPage entityPage,
                                      DeliverySearchCriteria deliverySearchCriteria);

    DeliveryResponseDTO findById(Long id);

    void changeStatusToInProgress(String username, Long deliveryId);

    void changeStatusToDone(String username, Long deliveryId);
}
