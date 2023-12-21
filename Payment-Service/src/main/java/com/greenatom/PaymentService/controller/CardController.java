package com.greenatom.paymentservice.controller;

import com.greenatom.paymentservice.domain.dto.CardRequestDto;
import com.greenatom.paymentservice.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/cards")
@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<Void> createCard(CardRequestDto cardRequestDto) {
        cardService.createCard(cardRequestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}