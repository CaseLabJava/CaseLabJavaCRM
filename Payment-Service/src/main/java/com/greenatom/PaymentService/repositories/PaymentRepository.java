package com.greenatom.PaymentService.repositories;

import com.greenatom.PaymentService.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByNumber(String number);

    Boolean existsByNumber(String number);
}
