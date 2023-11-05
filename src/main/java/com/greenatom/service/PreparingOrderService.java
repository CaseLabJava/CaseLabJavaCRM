package com.greenatom.service;

import com.greenatom.domain.dto.client.ClientResponseDTO;
import com.greenatom.domain.dto.preparing_order.PreparingOrderResponseDTO;

import java.util.List;

public interface PreparingOrderService {
    List<PreparingOrderResponseDTO> findPreparingOrdersPageByParams(Integer pageNumber, Integer pageSize, String status);
}
