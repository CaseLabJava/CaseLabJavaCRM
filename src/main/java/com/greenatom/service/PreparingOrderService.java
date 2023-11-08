package com.greenatom.service;

import com.greenatom.domain.dto.preparing_order.PreparingOrderResponseDTO;
import jakarta.annotation.Nullable;

import java.util.List;

public interface PreparingOrderService {

    List<PreparingOrderResponseDTO> findPreparingOrdersPageByParams(Integer pageNumber, Integer pageSize, String status);

    Void appointCollector(@Nullable Long employeeId , Long preparingOrderId);
}
