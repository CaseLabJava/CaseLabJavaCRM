package com.greenatom.service;

import com.greenatom.domain.dto.preparing_order.PreparingOrderResponseDTO;
import jakarta.annotation.Nullable;

import java.util.List;

public interface PreparingOrderService {

    List<PreparingOrderResponseDTO> findPreparingOrdersPageByParams(Integer pageNumber, Integer pageSize, String status);

    PreparingOrderResponseDTO findOne(Long id);

    void appointCollector(@Nullable Long employeeId , Long preparingOrderId);

    void finishPreparingOrder(@Nullable Long employeeId, Long preparingOrderId);
}
