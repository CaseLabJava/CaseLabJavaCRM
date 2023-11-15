package com.greenatom.service.impl;

import com.greenatom.domain.dto.preparing_order.PreparingOrderResponseDTO;
import com.greenatom.domain.entity.Delivery;
import com.greenatom.domain.entity.Employee;
import com.greenatom.domain.entity.PreparingOrder;
import com.greenatom.domain.enums.DeliveryStatus;
import com.greenatom.domain.enums.JobPosition;
import com.greenatom.domain.enums.PreparingOrderStatus;
import com.greenatom.domain.mapper.PreparingOrderMapper;
import com.greenatom.exception.EmployeeException;
import com.greenatom.exception.OrderException;
import com.greenatom.exception.PreparingOrderException;
import com.greenatom.repository.DeliveryRepository;
import com.greenatom.repository.EmployeeRepository;
import com.greenatom.repository.PreparingOrderRepository;
import com.greenatom.service.PreparingOrderService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class PreparingOrderServiceImpl implements PreparingOrderService {

    private final PreparingOrderRepository preparingOrderRepository;
    private final PreparingOrderMapper mapper;

    private final EmployeeRepository employeeRepository;

    private final DeliveryRepository deliveryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PreparingOrderResponseDTO> findPreparingOrdersPageByParams(Integer pageNumber, Integer pageSize, String status) {
        return preparingOrderRepository.findPreparingOrdersByPreparingOrderStatus(
                        PageRequest.of(pageNumber, pageSize), PreparingOrderStatus.valueOf(status))
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public PreparingOrderResponseDTO findOne(Long id) {
        PreparingOrder preparingOrder = preparingOrderRepository.findById(id)
                .orElseThrow(PreparingOrderException.CODE.NO_SUCH_PREPARING_ORDER::get);
        PreparingOrderResponseDTO dto = mapper.toDto(preparingOrder);
        dto.setOrderId(preparingOrder.getId());
        dto.setEmployeeId(preparingOrder.getEmployee().getId());
        return dto;
    }

    @Override
    @Transactional
    public void appointCollector(@Nullable Long employeeId, Long preparingOrderId) {
        PreparingOrder preparingOrder = preparingOrderRepository
                .findById(preparingOrderId)
                .orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        if (employeeId != null) {
            Employee employee = employeeRepository
                    .findById(employeeId)
                    .orElseThrow(EmployeeException.CODE.NO_SUCH_EMPLOYEE::get);
            if (!employee.getJobPosition().equals(JobPosition.WAREHOUSE_WORKER)) {
                throw PreparingOrderException.CODE.INCORRECT_ROLE.get();
            }
            preparingOrder.setEmployee(employee);
            preparingOrder.setPreparingOrderStatus(PreparingOrderStatus.IN_PROCESS);
        } else {
            autoAppoint(preparingOrder);
        }
    }

    @Override
    @Transactional
    public void finishPreparingOrder(Long employeeId, Long preparingOrderId) {
        PreparingOrder preparingOrder = preparingOrderRepository
                .findById(preparingOrderId)
                .orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        if (!Objects.equals(employeeId, preparingOrder.getEmployee().getId())) {
            throw PreparingOrderException.CODE.NO_PERMISSION.get();
        }
        if (!preparingOrder.getPreparingOrderStatus().equals(PreparingOrderStatus.IN_PROCESS)) {
            throw PreparingOrderException.CODE.CANNOT_FINISH_ORDER.get();
        }
        preparingOrder.setEndTime(Instant.now());
        preparingOrder.setPreparingOrderStatus(PreparingOrderStatus.DONE);
        deliveryRepository.save(Delivery.builder().
                order(preparingOrder.getOrder())
                .deliveryStatus(DeliveryStatus.WAITING_FOR_DELIVERY)
                .startTime(Instant.now())
                .build());
    }

    private void autoAppoint(PreparingOrder order) {
        //код для автоматического назначения сборщика
    }
}
