package com.autorizador.pocket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

public class CardTransactionException extends ResponseStatusException {
    public CardTransactionException(CardTransactionValidatorEnum transactionValidator){
        super(HttpStatus.UNPROCESSABLE_ENTITY, transactionValidator.name());
    }
}
