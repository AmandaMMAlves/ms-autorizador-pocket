package com.autorizador.pocket.http.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CardRequest {
    @NotBlank(message = "Número do cartão é obrigatório")
    @NotNull(message = "Número do cartão é obrigatório")
    @NotEmpty(message = "Número do cartão é obrigatório")
    @JsonProperty("numeroCartao")
    private String cardNumber;

    private String cpf;

    @JsonProperty("nome")
    private String name;

    @NotBlank(message = "Senha é obrigatória")
    @NotNull(message = "Senha é obrigatória")
    @NotEmpty(message = "Senha é obrigatória")
    @JsonProperty("senha")
    private String password;
}
