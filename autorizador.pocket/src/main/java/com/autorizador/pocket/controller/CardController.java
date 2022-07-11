package com.autorizador.pocket.controller;

import com.autorizador.pocket.request.CardRequest;
import com.autorizador.pocket.response.CardResponse;
import com.autorizador.pocket.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CardController {
    private final CardService service;

    @PostMapping
    public ResponseEntity<CardResponse> createCard(@RequestBody CardRequest cardRequest){
        return service.create(cardRequest);
    }

    @GetMapping("/{numeroCartao}")
    @ResponseBody
    public BigDecimal getCardBalance(@PathVariable("numeroCartao") String cardNumber){
        return service.getCardBalance(cardNumber);
    }

    @GetMapping
    public List<CardResponse> getCards(){
        return service.getAllCards();
    }

    @DeleteMapping
    public void deleteCard(@RequestBody CardRequest cardRequest){
        service.deleteCard(cardRequest);
    }

    @DeleteMapping("/all")
    public void deleteAllCards(){
        service.deleteAllCards();
    }
}
