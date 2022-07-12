package com.autorizador.pocket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class CardAlreadyCreatedException extends RuntimeException{
    public CardAlreadyCreatedException(){
        super();
    }
}
