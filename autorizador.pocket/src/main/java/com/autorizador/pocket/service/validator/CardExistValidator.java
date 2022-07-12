package com.autorizador.pocket.service.validator;

import com.autorizador.pocket.exception.CardTransactionException;
import com.autorizador.pocket.exception.CardTransactionValidatorEnum;
import com.autorizador.pocket.model.Card;
import com.autorizador.pocket.repository.CardRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Classe resposável pela validação da existencia do cartao
 * @author Amanda Mayara
 */
public class CardExistValidator extends CardTransactionValidator{
    @Override
    public boolean validate(Optional<Card> card, String password, BigDecimal value) {
        card.orElseThrow(() -> new CardTransactionException(CardTransactionValidatorEnum.CARTAO_INEXISTENTE));

        return checkNext(card, password, value);
    }
}
