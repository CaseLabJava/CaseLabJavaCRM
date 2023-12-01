package com.greenatom.PaymentService.controllers;


import com.greenatom.PaymentService.dto.PaymentDto;
import com.greenatom.PaymentService.entities.Card;
import com.greenatom.PaymentService.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/payment")
@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/pay")
    public ResponseEntity<Card> pay(PaymentDto paymentDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                        .body(paymentService.pay(paymentDto));
    }
}
