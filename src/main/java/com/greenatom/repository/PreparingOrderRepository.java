package com.greenatom.repository;

import com.greenatom.domain.entity.PreparingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreparingOrderRepository extends JpaRepository<PreparingOrder, Long> {
}
