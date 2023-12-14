package com.greenatom.paymentservice.repository;

import com.greenatom.paymentservice.domain.entity.Payment;
import com.greenatom.paymentservice.domain.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Modifying
    @Query("UPDATE Payment p SET p.status = :status WHERE p.id = :paymentId")
    int updatePaymentStatusById(@Param("paymentId") Long paymentId, @Param("status") PaymentStatus status);
}
