package com.autorizador.pocket.service.validator;

import com.autorizador.pocket.model.Card;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Classe abstrata resposavel pela validacao em cadeia utilizando
 * o Design Pattern Chain of Responsibility
 * @author Amanda Mayara
 */
public abstract class CardTransactionValidator {
    private CardTransactionValidator next;

    public CardTransactionValidator setNext(CardTransactionValidator next) {
        this.next = next;
        return next;
    }

    public abstract boolean validate(Optional<Card> card, String password, BigDecimal value);

    protected boolean checkNext(Optional<Card> card, String password, BigDecimal value) {
        return next.validate(card, password, value);
    }
}
