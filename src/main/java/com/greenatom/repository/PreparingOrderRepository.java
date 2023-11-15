package com.greenatom.repository;

import com.greenatom.domain.entity.PreparingOrder;
import com.greenatom.domain.enums.PreparingOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreparingOrderRepository extends JpaRepository<PreparingOrder, Long> {
    Page<PreparingOrder> findPreparingOrdersByPreparingOrderStatus(Pageable pageable, PreparingOrderStatus preparingOrderStatus);
}