package com.greenatom.PaymentService.controller;


import com.greenatom.PaymentService.controller.api.CardApi;
import com.greenatom.PaymentService.domain.dto.CardRequestDto;
import com.greenatom.PaymentService.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/cards")
@RestController
@RequiredArgsConstructor
public class CardController implements CardApi {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<Void> createCard(CardRequestDto cardRequestDto) {
        cardService.createCard(cardRequestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
