package com.greenatom.clientselfservice.controller;

import com.greenatom.clientselfservice.controller.api.PayApi;
import com.greenatom.clientselfservice.service.impl.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("self_service/payment")
@RequiredArgsConstructor
public class PayController implements PayApi {
    private final String paymentUrl = "http://CaseLabJavaCrm/api/payments/kafka";
    private final PayService payService;

    @PostMapping("/pay")
    public ResponseEntity<Void> pay(@RequestParam Long paymentId) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(paymentUrl)
                .queryParam("paymentId", paymentId);
        payService.pay(builder);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
