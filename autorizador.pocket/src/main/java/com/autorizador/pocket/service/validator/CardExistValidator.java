package com.autorizador.pocket.service.validator;

import com.autorizador.pocket.exception.CardTransactionException;
import com.autorizador.pocket.exception.CardTransactionValidatorEnum;
import com.autorizador.pocket.model.Card;
import com.autorizador.pocket.repository.CardRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

public class CardExistValidator extends CardTransactionValidator{
    @Override
    public boolean validate(Optional<Card> card, String password, BigDecimal value) {
        card.orElseThrow(() -> new CardTransactionException(CardTransactionValidatorEnum.CARTAO_INEXISTENTE));

        return checkNext(card, password, value);
    }
}
