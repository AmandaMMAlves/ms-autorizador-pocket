package com.autorizador.pocket.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static com.autorizador.pocket.mock.MockCard.mockRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
class CardControllerIT extends BaseControllerTest {

    @AfterEach
    private void afterEach() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Should create a new card")
    void createCardWithSuccess() throws Exception {
        var request = mockRequest();

        this.mockMvc.perform(post("/cartoes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cardRequestObjectAsJsonString(request)))
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
                .content(cardRequestObjectAsJsonString(mockRequest())))
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
}