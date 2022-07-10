package com.autorizador.pocket.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;

@Document
@Data
public class Card {
    @MongoId
    private String cardNumber;
    private String password;
    private BigDecimal balance;
}
