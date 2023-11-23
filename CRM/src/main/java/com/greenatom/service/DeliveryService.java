package com.greenatom.service;

import com.greenatom.domain.dto.delivery.DeliveryResponseDTO;
import com.greenatom.domain.dto.delivery.DeliverySearchCriteria;
import com.greenatom.domain.dto.EntityPage;
import org.springframework.data.domain.Page;

public interface DeliveryService {

    Page<DeliveryResponseDTO> findAll(EntityPage entityPage,
                                      DeliverySearchCriteria deliverySearchCriteria);

    DeliveryResponseDTO findById(Long id);

    void changeStatusToInProgress(String username, Long deliveryId);

    void changeStatusToDone(String username, Long deliveryId);
}
