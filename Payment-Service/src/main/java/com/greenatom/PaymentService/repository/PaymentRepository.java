package com.greenatom.PaymentService.repository;


import com.greenatom.PaymentService.domain.entity.Payment;
import com.greenatom.PaymentService.domain.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Modifying
    @Query("UPDATE Payment p SET p.status = :status WHERE p.id = :paymentId")
    int updatePaymentStatusById(@Param("paymentId") Long paymentId, @Param("status") PaymentStatus status);
}
