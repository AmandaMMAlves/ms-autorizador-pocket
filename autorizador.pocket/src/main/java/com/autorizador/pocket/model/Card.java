package com.autorizador.pocket.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "card")
@Data
@CompoundIndexes({
    @CompoundIndex(name = "card_index", def = "{'cardNumber' : 1, 'cpf': 1}")
})
public class Card {
    private String id;
    private String cardNumber;
    private String cpf;
    private String name;
    private String password;
    private BigDecimal balance;
}
