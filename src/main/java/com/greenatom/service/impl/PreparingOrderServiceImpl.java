package com.greenatom.service.impl;

import com.greenatom.domain.dto.preparing_order.PreparingOrderResponseDTO;
import com.greenatom.domain.enums.PreparingOrderStatus;
import com.greenatom.domain.mapper.PreparingOrderMapper;
import com.greenatom.repository.PreparingOrderRepository;
import com.greenatom.service.PreparingOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PreparingOrderServiceImpl implements PreparingOrderService {
    private final PreparingOrderRepository preparingOrderRepository;
    private final PreparingOrderMapper mapper;

    @Override
    public List<PreparingOrderResponseDTO> findPreparingOrdersPageByParams(Integer pageNumber, Integer pageSize, String status) {
        return preparingOrderRepository.findPreparingOrdersByPreparingOrderStatus(
                PageRequest.of(pageNumber, pageSize), PreparingOrderStatus.valueOf(status))
                .map(mapper::toDto)
                .toList();
    }
}
