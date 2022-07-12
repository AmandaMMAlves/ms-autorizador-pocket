package com.autorizador.pocket.mapper;

import com.autorizador.pocket.model.Card;
import com.autorizador.pocket.http.request.CardRequest;
import com.autorizador.pocket.http.response.CardResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {

    Card requestToModel(CardRequest request);
    CardResponse modelToResponse(Card model);

    CardResponse requestToResponse(CardRequest request);
}
