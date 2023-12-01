package com.greenatom.PaymentService.services;

import com.greenatom.PaymentService.dto.PaymentDto;
import com.greenatom.PaymentService.entities.Card;
import com.greenatom.PaymentService.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    public Card pay(PaymentDto paymentDto) {
        String cardNumber = paymentDto.getNumber();

        if (paymentRepository.existsByNumber(cardNumber)) {
            return paymentRepository
                    .findByNumber(cardNumber)
                    .orElseThrow();
        }
        else {
            Card card = new Card();
            card.setNumber(cardNumber);
            card.setCardholder(paymentDto.getCardholder());
            card.setExpiredDate(paymentDto.getExpiredDate());
            card.setCvv(paymentDto.getCvv());
            return paymentRepository.save(card);
        }
    }
}
