package com.autorizador.pocket.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardRequest {
    @JsonProperty("numeroCartao")
    private String cardNumber;

    private String cpf;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("senha")
    private String password;
}
