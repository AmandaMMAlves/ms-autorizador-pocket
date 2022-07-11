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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private void afterEach() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Should create a new card")
    void createCardWithSucces() throws Exception {
        var request = mockRequest();

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
        initCardData();

        this.mockMvc.perform(post("/cartoes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cardRequestAsJsonString(mockRequest())))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Should return card balance")
    void returnCardBalanceWithSuccess() throws Exception {
        initCardData();

        this.mockMvc.perform(get("/cartoes/1234567890"))
            .andExpect(status().isOk())
            .andExpect(content().string("500"));
    }

    @Test
    @DisplayName("Should not return card balance")
    void notReturnCardBalanceWithSuccess() throws Exception {
        this.mockMvc.perform(get("/cartoes/1234567890"))
            .andExpect(status().isNotFound());
    }

    private void initCardData() {
        var request = mockRequest();

        service.create(request);
    }

    private CardRequest mockRequest() {
        return CardRequest.builder()
            .cardNumber("1234567890")
            .cpf("78931227899")
            .password("1234")
            .name("Meryl Streep")
            .build();
    }

    private static String cardRequestAsJsonString(CardRequest request) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(request);
    }
}