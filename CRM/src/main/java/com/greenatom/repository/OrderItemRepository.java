package com.greenatom.repository;

import com.greenatom.domain.entity.OrderItem;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByOrderId(@NotNull Long id);

    List<OrderItem> findOrderItemsByProductId(Long id);
}
