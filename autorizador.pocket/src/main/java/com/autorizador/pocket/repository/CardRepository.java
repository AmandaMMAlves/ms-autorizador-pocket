package com.autorizador.pocket.repository;

import com.autorizador.pocket.model.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends MongoRepository<Card, String> {

    Optional<Card> findByCardNumber(String cardNumber);
}
