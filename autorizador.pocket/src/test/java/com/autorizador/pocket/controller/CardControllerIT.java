package com.autorizador.pocket.controller;

import com.autorizador.pocket.repository.CardRepository;
import com.autorizador.pocket.request.CardRequest;
import com.autorizador.pocket.service.CardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CardControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardRepository repository;

    @Autowired
    private CardService service;

    @AfterEach
    private void init(){
        repository.deleteAll();
    }

    @Test
    @DisplayName("Should create a new card")
    void createCardWithSucces() throws Exception {
        var request  = CardRequest.builder()
            .cardNumber("1234567890")
            .cpf("78931227899")
            .password("1234")
            .name("Meryl Streep")
            .build();

        this.mockMvc.perform(post("/cartoes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cardRequestAsJsonString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.numeroCartao").value(request.getCardNumber()))
            .andExpect(jsonPath("$.senha").value(request.getPassword()));

        var card = repository.findByCardNumber(request.getCardNumber());
        assertTrue(card.isPresent());
        assertEquals(card.get().getBalance(), BigDecimal.valueOf(500L));
    }

    @Test
    @DisplayName("Should not create a duplicated card")
    void notCreateDuplicatedCard() throws Exception {
        var request  = CardRequest.builder()
            .cardNumber("1234567890")
            .cpf("78931227899")
            .password("1234")
            .name("Meryl Streep")
            .build();

        service.create(request);

        this.mockMvc.perform(post("/cartoes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cardRequestAsJsonString(request)))
            .andExpect(status().isUnprocessableEntity());
    }

    private static String cardRequestAsJsonString(CardRequest request) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(request);
    }
}