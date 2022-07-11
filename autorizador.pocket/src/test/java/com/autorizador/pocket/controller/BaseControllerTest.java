package com.autorizador.pocket.controller;

import com.autorizador.pocket.repository.CardRepository;
import com.autorizador.pocket.service.CardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static com.autorizador.pocket.mock.MockCard.mockRequest;

public class BaseControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected CardRepository repository;

    @Autowired
    protected CardService service;

    protected void initCardData() {
        var request = mockRequest();

        service.create(request);
    }

    protected static String cardRequestObjectAsJsonString(Object request) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(request);
    }
}
