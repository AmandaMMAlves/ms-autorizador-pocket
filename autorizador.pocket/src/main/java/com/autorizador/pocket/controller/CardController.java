package com.autorizador.pocket.controller;

import com.autorizador.pocket.exception.CardAlreadyCreatedException;
import com.autorizador.pocket.mapper.CardMapper;
import com.autorizador.pocket.http.request.CardRequest;
import com.autorizador.pocket.http.response.CardResponse;
import com.autorizador.pocket.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * Controller responsável pelos dados dos cartões
 * @author Amanda Mayara
 */
@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CardController {
    private final CardService service;

    private final CardMapper mapper;

    @PostMapping
    public ResponseEntity<CardResponse> createCard(@Valid @RequestBody CardRequest cardRequest) {
        try {
            var cardResponse = mapper.modelToResponse(service.create(mapper.requestToModel(cardRequest)));
            return ResponseEntity.status(HttpStatus.CREATED).body(cardResponse);
        } catch (CardAlreadyCreatedException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(mapper.requestToResponse(cardRequest));
        }

    }

    @GetMapping("/{numeroCartao}")
    @ResponseBody
    public BigDecimal getCardBalance(@PathVariable(value = "numeroCartao", required = true) String cardNumber) {
        return service.getCardBalance(cardNumber);
    }
}
