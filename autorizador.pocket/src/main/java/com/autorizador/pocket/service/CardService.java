package com.autorizador.pocket.service;

import com.autorizador.pocket.exception.CardNotFoundException;
import com.autorizador.pocket.exception.CardTransactionException;
import com.autorizador.pocket.exception.CardTransactionValidatorEnum;
import com.autorizador.pocket.mapper.CardMapper;
import com.autorizador.pocket.model.Card;
import com.autorizador.pocket.repository.CardRepository;
import com.autorizador.pocket.request.CardRequest;
import com.autorizador.pocket.response.CardResponse;
import com.autorizador.pocket.service.validator.BalanceValidator;
import com.autorizador.pocket.service.validator.CardExistValidator;
import com.autorizador.pocket.service.validator.CardTransactionValidator;
import com.autorizador.pocket.service.validator.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository repository;
    private final CardMapper mapper;

    private final CardTransactionValidator transactionValidator = new CardExistValidator();

    public ResponseEntity<CardResponse> create(CardRequest request) {
        return repository.findByCardNumber(request.getCardNumber())
            .map(card -> ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(mapper.modelToResponse(card)))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.CREATED).body(initCard(request)));
    }

    public BigDecimal getCardBalance(String cardNumber){
        return repository.findByCardNumber(cardNumber)
            .map(Card::getBalance)
            .orElseThrow(CardNotFoundException::new);
    }

    public boolean authorizeCardTransaction(String cardNumber, String password, BigDecimal value){
        transactionValidator.setNext(new PasswordValidator()).setNext(new BalanceValidator(repository));
        return transactionValidator.validate(repository.findByCardNumber(cardNumber), password, value);
    }

    public List<CardResponse> getAllCards() {
        var cards = repository.findAll();
        return cards.stream().map(mapper::modelToResponse).toList();
    }

    public void deleteCard(CardRequest request) {
        Card card = mapper.requestToModel(request);
        repository.delete(card);
    }

    public void deleteAllCards(){
        repository.deleteAll();
    }

    private CardResponse initCard(CardRequest request) {
        Card newCard = mapper.requestToModel(request);
        newCard.setBalance(BigDecimal.valueOf(500L));
        return mapper.modelToResponse(repository.save(newCard));
    }
}
