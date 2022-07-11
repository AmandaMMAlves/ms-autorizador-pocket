package com.autorizador.pocket.controller;

import com.autorizador.pocket.request.CardBalanceRequest;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
class CardTransactionControllerIT extends BaseControllerTest{

    @AfterEach
    private void afterEach() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Should authorize card transaction and return 201 status")
    void shouldAuthorizeCardTransaction() throws Exception {
        initCardData();
        var balanceRequest = mockBalanceRequest();

        this.mockMvc.perform(post("/transacoes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cardRequestObjectAsJsonString(balanceRequest)))
            .andExpect(status().isCreated())
            .andExpect(content().string("Ok"));

        var card = repository.findByCardNumber(balanceRequest.getCardNumber());
        assertTrue(card.isPresent());
        assertEquals(BigDecimal.valueOf(200L),card.get().getBalance());
    }

    @Test
    @DisplayName("Should not authorize card transaction for a card not found and return 422")
    void shouldNotAuthorizeCardTransactionCardNotFound() throws Exception {
        var balanceRequest = mockBalanceRequest();

        this.mockMvc.perform(post("/transacoes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cardRequestObjectAsJsonString(balanceRequest)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(status().reason("CARTAO_INEXISTENTE"));
    }

    @Test
    @DisplayName("Should not authorize card transaction for invalid password and return 422")
    void shouldNotAuthorizeCardTransactionInvalidPassword() throws Exception {
        initCardData();
        var balanceRequest = mockBalanceRequest();
        balanceRequest.setPassword("0987");

        this.mockMvc.perform(post("/transacoes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cardRequestObjectAsJsonString(balanceRequest)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(status().reason("SENHA_INVALIDA"));
    }

    @Test
    @DisplayName("Should not authorize card transaction for insufficient balance")
    void shouldNotAuthorizeCardTransactionInsufficientBalance() throws Exception {
        initCardData();
        var balanceRequest = mockBalanceRequest();
        balanceRequest.setValue(BigDecimal.valueOf(600L));

        this.mockMvc.perform(post("/transacoes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cardRequestObjectAsJsonString(balanceRequest)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(status().reason("SALDO_INSUFICIENTE"));
    }


    private CardBalanceRequest mockBalanceRequest() {
        return CardBalanceRequest.builder()
            .cardNumber("1234567890")
            .password("1234")
            .value(BigDecimal.valueOf(300L)).build();
    }

}