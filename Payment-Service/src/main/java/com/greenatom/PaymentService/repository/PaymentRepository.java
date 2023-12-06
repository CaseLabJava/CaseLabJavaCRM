package com.greenatom.paymentservice.repository;

import com.greenatom.paymentservice.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByUserIdAndOrderId(Long userId, Long orderId);
    Boolean existsByUserIdAndOrderId(Long userId, Long orderId);
}
