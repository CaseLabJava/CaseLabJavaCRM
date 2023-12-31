package com.greenatom.PaymentService.controller;


import com.greenatom.PaymentService.controller.api.PaymentApi;
import com.greenatom.PaymentService.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/payments")
@RestController
@RequiredArgsConstructor
public class PaymentController implements PaymentApi {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Void> createPayment(
            @RequestParam Long userId,
            @RequestParam Long orderId,
            @RequestParam Long sumOfPay) {
        paymentService.createPayment(userId, orderId, sumOfPay);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/kafka")
    public void send(@RequestParam Long paymentId) {
        paymentService.initiatePayment(paymentId);
    }
}
