package com.greenatom.service;

import com.greenatom.domain.dto.preparing_order.PreparingOrderResponseDTO;
import jakarta.annotation.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PreparingOrderService {
    @Transactional(readOnly = true)
    List<PreparingOrderResponseDTO> findPreparingOrdersPageByParams(Integer pageNumber, Integer pageSize, String status);
    @Transactional
    Void appointCollector(@Nullable Long employeeId , Long preparingOrderId);
}
