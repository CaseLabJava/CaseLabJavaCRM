package com.greenatom.paymentservice.repository;

import com.greenatom.paymentservice.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
