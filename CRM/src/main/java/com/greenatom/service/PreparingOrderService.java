package com.greenatom.service;

import com.greenatom.domain.dto.EntityPage;
import com.greenatom.domain.dto.preparing_order.PreparingOrderResponseDTO;
import com.greenatom.domain.dto.preparing_order.PreparingOrderSearchCriteria;
import org.springframework.data.domain.Page;

public interface PreparingOrderService {

    Page<PreparingOrderResponseDTO> findAll(EntityPage entityPage,
                                            PreparingOrderSearchCriteria preparingOrderSearchCriteria);

    PreparingOrderResponseDTO findOne(Long id);

    void appointCollector(String username, Long preparingOrderId);

    void finishPreparingOrder(String username, Long preparingOrderId);
}
