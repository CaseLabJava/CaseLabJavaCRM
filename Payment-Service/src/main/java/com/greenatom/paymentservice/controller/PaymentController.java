package com.greenatom.paymentservice.controller;


import com.greenatom.paymentservice.domain.dto.PaymentRequestDto;
import com.greenatom.paymentservice.domain.dto.PaymentResponseDto;
import com.greenatom.paymentservice.service.PaymentService;
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
    public ResponseEntity<PaymentResponseDto> pay(PaymentRequestDto paymentDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                        .body(paymentService.pay(paymentDto));
    }
}
