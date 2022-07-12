package com.autorizador.pocket.controller;

import com.autorizador.pocket.mapper.CardMapper;
import com.autorizador.pocket.model.Card;
import com.autorizador.pocket.repository.CardRepository;
import com.autorizador.pocket.request.CardRequest;
import com.autorizador.pocket.service.CardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static com.autorizador.pocket.mock.MockCard.mockRequest;

public class BaseControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected CardRepository repository;

    @Autowired
    protected CardService service;

    protected void initCardData() {
        var card = new Card();
        card.setName("Meryl Streep");
        card.setCpf("78931227899");
        card.setBalance(new BigDecimal(500L));
        card.setCardNumber("1234567890");
        card.setPassword("1234");

        repository.insert(card);
    }

    protected static String cardRequestObjectAsJsonString(Object request) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(request);
    }
}
