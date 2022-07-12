package com.autorizador.pocket.controller;

import com.autorizador.pocket.http.request.CardBalanceRequest;
import com.autorizador.pocket.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsável pelas transações dos cartões
 * @author Amanda Mayara
 */
@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class CardTransactionController {
    private final CardService service;

    @PostMapping
    public ResponseEntity<String> authorizeTransaction(@RequestBody CardBalanceRequest cardRequest) {
        service.authorizeCardTransaction(cardRequest.getCardNumber(), cardRequest.getPassword(), cardRequest.getValue());

        return ResponseEntity.status(HttpStatus.CREATED).body("Ok");
    }
}
