package com.autorizador.pocket.service;

import com.autorizador.pocket.exception.CardAlreadyCreatedException;
import com.autorizador.pocket.exception.CardNotFoundException;
import com.autorizador.pocket.mapper.CardMapper;
import com.autorizador.pocket.model.Card;
import com.autorizador.pocket.repository.CardRepository;
import com.autorizador.pocket.service.validator.BalanceValidator;
import com.autorizador.pocket.service.validator.CardExistValidator;
import com.autorizador.pocket.service.validator.CardTransactionValidator;
import com.autorizador.pocket.service.validator.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service responsável pelo tráfego dos cartões
 * @author Amanda Mayara
 */
@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository repository;

    private final CardTransactionValidator transactionValidator = new CardExistValidator();

    public Card create(Card card) {
        return repository.findByCardNumber(card.getCardNumber())
            .filter(c -> {throw new CardAlreadyCreatedException();})
            .orElseGet(() -> initCard(card));
    }


    public BigDecimal getCardBalance(String cardNumber){
        return repository.findByCardNumber(cardNumber)
            .map(Card::getBalance)
            .orElseThrow(CardNotFoundException::new);
    }

    public synchronized boolean authorizeCardTransaction(String cardNumber, String password, BigDecimal value){
        transactionValidator.setNext(new PasswordValidator()).setNext(new BalanceValidator(repository));
        return transactionValidator.validate(repository.findByCardNumber(cardNumber), password, value);
    }
    private Card initCard(Card newCard) {
        newCard.setBalance(BigDecimal.valueOf(500L));
        return repository.save(newCard);
    }
}
