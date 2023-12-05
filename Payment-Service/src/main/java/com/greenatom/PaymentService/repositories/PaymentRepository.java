package com.greenatom.PaymentService.repositories;

import com.greenatom.PaymentService.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByUserIdAndOrderId(Long user_id, Long order_id);
    Boolean existsByUserIdAndOrderId(Long user_id, Long order_id);
}
