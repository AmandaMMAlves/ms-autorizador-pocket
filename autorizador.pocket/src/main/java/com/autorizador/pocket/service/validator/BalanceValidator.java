package com.autorizador.pocket.service.validator;

import com.autorizador.pocket.exception.CardTransactionException;
import com.autorizador.pocket.exception.CardTransactionValidatorEnum;
import com.autorizador.pocket.model.Card;
import com.autorizador.pocket.repository.CardRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;
@RequiredArgsConstructor
public class BalanceValidator extends CardTransactionValidator {
    private final CardRepository repository;

    @Override
    public boolean validate(Optional<Card> card, String password, BigDecimal value) {
        card.filter(c -> c.getBalance().compareTo(value) >= 0)
            .map(c -> {
                var newBalance = c.getBalance().subtract(value);
                c.setBalance(newBalance);
                return repository.save(c);
            })
            .orElseThrow(() -> new CardTransactionException(CardTransactionValidatorEnum.SALDO_INSUFICIENTE));

        return true;
    }
}
