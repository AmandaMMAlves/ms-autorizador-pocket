package com.autorizador.pocket.service.validator;

import com.autorizador.pocket.model.Card;
import com.autorizador.pocket.repository.CardRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

public abstract class CardTransactionValidator {
    private CardTransactionValidator next;

    /**
     * Builds chains of middleware objects.
     */
    public CardTransactionValidator setNext(CardTransactionValidator next) {
        this.next = next;
        return next;
    }

    /**
     * Subclasses will implement this method with concrete checks.
     */
    public abstract boolean validate(Optional<Card> card, String password, BigDecimal value);

    /**
     * Runs check on the next object in chain or ends traversing if we're in
     * last object in chain.
     */
    protected boolean checkNext(Optional<Card> card, String password, BigDecimal value) {
//        if (next == null) {
//            return true;
//        }
        return next.validate(card, password, value);
    }
}
