package com.autorizador.pocket.service.validator;

import com.autorizador.pocket.exception.CardTransactionException;
import com.autorizador.pocket.exception.CardTransactionValidatorEnum;
import com.autorizador.pocket.model.Card;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Classe resposável pela validação da senha
 * @author Amanda Mayara
 */
public class PasswordValidator extends CardTransactionValidator{
    @Override
    public boolean validate(Optional<Card> card, String password, BigDecimal value) {
        card.filter(c -> c.getPassword().equals(password))
            .orElseThrow(() -> new CardTransactionException(CardTransactionValidatorEnum.SENHA_INVALIDA));
        return checkNext(card, password, value);
    }
}
