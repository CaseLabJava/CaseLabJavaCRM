package com.greenatom.service.impl;

import com.greenatom.domain.dto.preparing_order.PreparingOrderResponseDTO;
import com.greenatom.domain.entity.Employee;
import com.greenatom.domain.entity.Order;
import com.greenatom.domain.entity.PreparingOrder;
import com.greenatom.domain.enums.PreparingOrderStatus;
import com.greenatom.domain.mapper.PreparingOrderMapper;
import com.greenatom.repository.EmployeeRepository;
import com.greenatom.repository.PreparingOrderRepository;
import com.greenatom.service.PreparingOrderService;
import com.greenatom.utils.exception.EmployeeException;
import com.greenatom.utils.exception.OrderException;
import jakarta.annotation.Nullable;
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
    private final EmployeeRepository employeeRepository;

    @Override
    public List<PreparingOrderResponseDTO> findPreparingOrdersPageByParams(Integer pageNumber, Integer pageSize, String status) {
        return preparingOrderRepository.findPreparingOrdersByPreparingOrderStatus(
                        PageRequest.of(pageNumber, pageSize), PreparingOrderStatus.valueOf(status))
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public Void appointCollector(@Nullable Long employeeId, Long preparingOrderId) {
        PreparingOrder order = preparingOrderRepository.findById(preparingOrderId).orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        if (employeeId != null) {
            Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeException.CODE.NO_SUCH_EMPLOYEE::get);
            order.setEmployee(employee);
        } else {
            autoAppoint(order);
        }
        return null;
    }

    private void autoAppoint(PreparingOrder order) {
        //код для автоматического назначения сборщика
    }
}
