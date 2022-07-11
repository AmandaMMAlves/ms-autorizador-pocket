package com.autorizador.pocket.mock;

import com.autorizador.pocket.request.CardRequest;

public class MockCard {

    public static CardRequest mockRequest() {
        return CardRequest.builder()
            .cardNumber("1234567890")
            .cpf("78931227899")
            .password("1234")
            .name("Meryl Streep")
            .build();
    }
}
